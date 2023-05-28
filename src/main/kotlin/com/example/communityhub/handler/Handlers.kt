package com.example.communityhub.handler

import com.example.communityhub.handler.usermanagement.UserLoginHandler
import com.example.communityhub.handler.usermanagement.UserRefreshTokenHandler
import com.example.communityhub.handler.usermanagement.UserSignupHandler
import org.springframework.stereotype.Component

@Component
data class Handlers(
	val userHandlers: UserHandlers,
)

@Component
data class UserHandlers(
	val userLoginHandler: UserLoginHandler,
	val userSignupHandler: UserSignupHandler,
	val userRefreshTokenHandler: UserRefreshTokenHandler,
)