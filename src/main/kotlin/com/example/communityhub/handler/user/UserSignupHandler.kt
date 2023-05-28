package com.example.communityhub.handler.user

import com.example.communityhub.constant.Message
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.UserInfoDao
import com.example.communityhub.dao.model.UserInfo
import com.example.communityhub.exception.badRequestException
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.service.JwtService
import com.example.communityhub.service.SessionInfo
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

		val id = request.id

		if (id.isNullOrEmpty()) {
			throw badRequestException(Message.INVALID_REQUEST)
		}
		if (request.password.isNullOrEmpty()) {
			throw badRequestException(Message.INVALID_REQUEST)
		}
		if (request.name.isNullOrEmpty()) {
			throw badRequestException(Message.INVALID_REQUEST)
		}

		val repository = userInfoDao.repository()
		repository.findById(id).ifPresent {throw badRequestException(Message.USER_ALREADY_PRESENT) }

		val currentTimeMillis = System.currentTimeMillis()

		val userInfo = repository.insert(UserInfo(
			id = id,
			name = request.name,
			password = passwordEncoder.encode(request.password),
			created = currentTimeMillis,
			updated = currentTimeMillis,
		))

		val sessionInfo = jwtService.getSessionInfo(userInfo)

		return ResponseEntity.ok(UserSignupResponse(
			message = Message.CREATED,
			id = userInfo.id,
			name = userInfo.name,
			sessionInfo = sessionInfo,
		))
	}

}

data class UserSignupRequest(
	val id: String? = null,
	val name: String? = null,
	val password: String? = null,
)
data class UserSignupResponse(
	override var message: String?=null,
	val id: String? = null,
	val name: String? = null,
	val sessionInfo: SessionInfo? = null
): BaseResponse(message)