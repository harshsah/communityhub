package com.example.communityhub.handler

import com.example.communityhub.handler.user.*
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
	val userProfileHandler: UserProfileHandler,
	val userProfileUpdateHandler: UserProfileUpdateHandler,
)