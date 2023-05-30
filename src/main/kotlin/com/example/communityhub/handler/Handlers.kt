package com.example.communityhub.handler

import com.example.communityhub.handler.community.CommunityCreateHandler
import com.example.communityhub.handler.community.CommunityGetHandler
import com.example.communityhub.handler.post.PostCreateHandler
import com.example.communityhub.handler.user.*
import org.springframework.stereotype.Component

@Component
data class Handlers(
	val userHandlers: UserHandlers,
	val communityHandlers: CommunityHandlers,
	val postHandlers: PostHandlers,
)

@Component
data class UserHandlers(
	val userLoginHandler: UserLoginHandler,
	val userSignupHandler: UserSignupHandler,
	val userRefreshTokenHandler: UserRefreshTokenHandler,
	val userProfileHandler: UserProfileHandler,
	val userProfileUpdateHandler: UserProfileUpdateHandler,
)

@Component
data class CommunityHandlers(
	val communityCreateHandler: CommunityCreateHandler,
	val communityGetHandler: CommunityGetHandler,
)

@Component
data class PostHandlers(
	val postCreateHandler: PostCreateHandler,
)