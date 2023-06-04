package com.example.communityhub.controller

import com.example.communityhub.handler.Handlers
import com.example.communityhub.handler.user.*
import com.example.communityhub.utils.HeaderUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/user")
class UserController (
	private val handlers: Handlers,
) {

	@PostMapping("/auth/login")
	fun signup(@RequestBody userLoginRequest: UserLoginRequest) =
		handlers.userHandlers.userLoginHandler.handle(userLoginRequest)

	@PostMapping("/auth/signup")
	fun login(@RequestBody userSignupRequest: UserSignupRequest) =
		handlers.userHandlers.userSignupHandler.handle(userSignupRequest)

	@GetMapping("/auth/refresh")
	fun refreshToken(servletRequest: HttpServletRequest) =
		handlers.userHandlers.userRefreshTokenHandler
			.handle(UserRefreshTokenRequest(HeaderUtils.getHeaders(servletRequest)))

	@GetMapping("/{userId}")
	fun userProfileGet(
		servletRequest: HttpServletRequest,
		@PathVariable("userId") userId: String,
	) = handlers.userHandlers.userProfileHandler
		.handle(UserProfileRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			userId = userId
		))

	@PostMapping("/{userId}/update")
	fun userProfileUpdate(
		servletRequest: HttpServletRequest,
		@RequestBody request: UserProfileUpdateRequest,
	) = handlers.userHandlers.userProfileUpdateHandler
		.handle(UserProfileUpdateRequest(
			httpHeaders = HeaderUtils.getHeaders(servletRequest),
			userInfoData = request.userInfoData
		))

}