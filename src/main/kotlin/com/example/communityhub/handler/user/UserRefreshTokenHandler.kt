package com.example.communityhub.handler.user

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.logging.LoggingGsonExclude
import com.example.communityhub.service.JwtService
import com.example.communityhub.service.SessionInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class UserRefreshTokenHandler(
	private val jwtService: JwtService,
): AbsHandler<UserRefreshTokenRequest, UserRefreshTokenResponse>(
	apiName = "user login",
	errorResponseSupplier = { UserRefreshTokenResponse() }
) {

	override suspend fun perform(request: UserRefreshTokenRequest): ResponseEntity<UserRefreshTokenResponse> {
		val userToken = jwtService.verifyToken(request.httpHeaders)
		val sessionInfo = jwtService.getSessionInfo(userToken)
		return ResponseEntity.ok(UserRefreshTokenResponse(
				message = MessageConstant.OK,
				sessionInfo = sessionInfo,
			))
	}
}

data class UserRefreshTokenRequest(
	@JsonIgnore
	@LoggingGsonExclude
	override val httpHeaders: HttpHeaders
): BaseRequest(httpHeaders)

data class UserRefreshTokenResponse(
	@LoggingGsonExclude
	override var message: String? = null,
	val sessionInfo: SessionInfo? = null,
): BaseResponse(message)