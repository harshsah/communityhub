package com.example.communityhub.handler.usermanagement

import com.example.communityhub.constant.Message
import com.example.communityhub.controller.request.BaseRequest
import com.example.communityhub.controller.response.BaseResponse
import com.example.communityhub.handler.AbsHandler
import com.example.communityhub.service.JwtService
import com.example.communityhub.service.SessionInfo
import com.example.communityhub.utils.HeaderUtils
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class UserRefreshTokenHandler(
	private val headerUtils: HeaderUtils,
	private val jwtService: JwtService,
): AbsHandler<UserRefreshTokenRequest, UserRefreshTokenResponse>(
	apiName = "user login",
	errorResponseSupplier = { UserRefreshTokenResponse() }
) {

	override suspend fun perform(request: UserRefreshTokenRequest): ResponseEntity<UserRefreshTokenResponse> {
		val userToken = headerUtils.verifyToken(request.httpHeaders)
		val sessionInfo = jwtService.getSessionInfo(userToken)
		return ResponseEntity.ok(UserRefreshTokenResponse(
				message = Message.OK,
				sessionInfo = sessionInfo,
			))
	}
}

data class UserRefreshTokenRequest(
	@JsonIgnore
	override val httpHeaders: HttpHeaders
): BaseRequest(httpHeaders)

data class UserRefreshTokenResponse(
	override var message: String? = null,
	val sessionInfo: SessionInfo? = null,
): BaseResponse(message)