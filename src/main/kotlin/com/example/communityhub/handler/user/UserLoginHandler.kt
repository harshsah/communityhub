package com.example.communityhub.handler.user

import com.example.communityhub.constant.Message
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.dao.impl.UserInfoDao
import com.example.communityhub.exception.badRequestException
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.logging.LoggingMask
import com.example.communityhub.service.JwtService
import com.example.communityhub.service.SessionInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserLoginHandler(
	private val userInfoDao: UserInfoDao,
	private val passwordEncoder: PasswordEncoder,
	private val jwtService: JwtService,
): AbsHandler<UserLoginRequest, UserLoginResponse>(
	apiName = "user login",
	errorResponseSupplier = { UserLoginResponse() }
) {

	override suspend fun perform(request: UserLoginRequest): ResponseEntity<UserLoginResponse> {

		val id = request.id?.lowercase() ?: throw badRequestException(Message.INVALID_REQUEST)
		val password = request.password ?: throw badRequestException(Message.INVALID_REQUEST)

		val userInfo = userInfoDao.findById(id) ?: throw badRequestException(Message.USER_NOT_FOUND)

		if (!passwordEncoder.matches(password, userInfo.password)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(UserLoginResponse(message = Message.UNAUTHORIZED))
		}

		val sessionInfo = jwtService.getSessionInfo(userInfo)

		return ResponseEntity.ok(UserLoginResponse(
			message = Message.OK,
			sessionInfo = sessionInfo,
		))
	}

}

data class UserLoginRequest(
	val id: String?,
	@LoggingMask
	val password: String?,
)

data class UserLoginResponse(
	@LoggingGsonExclude
	override var message: String? = null,
	val sessionInfo: SessionInfo? = null,
): BaseResponse(message)

