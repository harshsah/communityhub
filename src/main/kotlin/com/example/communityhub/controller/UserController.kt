package com.example.communityhub.controller

import com.example.communityhub.handler.Handlers
import com.example.communityhub.handler.user.UserLoginRequest
import com.example.communityhub.handler.user.UserSignupRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (
	private val handlers: Handlers
) {

	@PostMapping("/login")
	fun signup(@RequestBody userLoginRequest: UserLoginRequest) =
		handlers.userHandlers.userLoginHandler.handle(userLoginRequest)

	@PostMapping("/signup")
	fun login(@RequestBody userSignupRequest: UserSignupRequest) =
		handlers.userHandlers.userSignupHandler.handle(userSignupRequest)
}