package com.example.communityhub.handler.user

import com.example.communityhub.constant.Message
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.UserInfoDao
import com.example.communityhub.dao.model.UserInfo
import com.example.communityhub.dao.model.UserInfoContact
import com.example.communityhub.dao.model.UserInfoData
import com.example.communityhub.exception.badRequestException
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.logging.LoggingMask
import com.example.communityhub.service.JwtService
import com.example.communityhub.service.SessionInfo
import com.example.communityhub.utils.StringUtils.isAlphaNumeric
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserSignupHandler (
	private val userInfoDao: UserInfoDao,
	private val passwordEncoder: PasswordEncoder,
	private val jwtService: JwtService,
) : AbsHandler<UserSignupRequest, UserSignupResponse> (
	apiName = "user signup",
	errorResponseSupplier = {UserSignupResponse()}
) {
	override suspend  fun perform(request: UserSignupRequest): ResponseEntity<UserSignupResponse> {

		val id = request.id?.lowercase()

		if (id.isNullOrEmpty() || !id.isAlphaNumeric()) {
			throw badRequestException(Message.INVALID_REQUEST)
		}
		if (request.password.isNullOrEmpty()) {
			throw badRequestException(Message.INVALID_REQUEST)
		}

		val repository = userInfoDao.repository()
		repository.findById(id).ifPresent {throw badRequestException(Message.USER_ALREADY_PRESENT) }

		val currentTimeMillis = System.currentTimeMillis()

		val userInfo = repository.insert(UserInfo(
			id = id,
			userInfoData = UserInfoData(),
			userInfoContact = UserInfoContact(),
			password = passwordEncoder.encode(request.password),
			created = currentTimeMillis,
			updated = currentTimeMillis,
		))

		val sessionInfo = jwtService.getSessionInfo(userInfo)

		return ResponseEntity.ok(UserSignupResponse(
			message = Message.CREATED,
			sessionInfo = sessionInfo,
		))
	}

}

data class UserSignupRequest(
	val id: String? = null,
	@LoggingMask
	val password: String? = null,
)
data class UserSignupResponse(
	@LoggingGsonExclude
	override var message: String?=null,
	val sessionInfo: SessionInfo? = null
): BaseResponse(message)