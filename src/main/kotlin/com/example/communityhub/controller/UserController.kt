package com.example.communityhub.controller

import com.example.communityhub.handler.Handlers
import com.example.communityhub.handler.user.UserLoginRequest
import com.example.communityhub.handler.user.UserRefreshTokenRequest
import com.example.communityhub.handler.user.UserSignupRequest
import com.example.communityhub.utils.HeaderUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController (
	private val handlers: Handlers,
	private val headerUtils: HeaderUtils,
) {

	@PostMapping("/login")
	fun signup(@RequestBody userLoginRequest: UserLoginRequest) =
		handlers.userHandlers.userLoginHandler.handle(userLoginRequest)

	@PostMapping("/signup")
	fun login(@RequestBody userSignupRequest: UserSignupRequest) =
		handlers.userHandlers.userSignupHandler.handle(userSignupRequest)


	@GetMapping("/refresh")
	fun refreshToken(servletRequest: HttpServletRequest) =
		handlers.userHandlers.userRefreshTokenHandler
			.handle(UserRefreshTokenRequest(headerUtils.getHeaders(servletRequest)))
}