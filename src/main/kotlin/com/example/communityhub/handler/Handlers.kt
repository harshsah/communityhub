package com.example.communityhub.handler

import com.example.communityhub.handler.comment.CommentCreateHandler
import com.example.communityhub.handler.comment.CommentGetHandler
import com.example.communityhub.handler.comment.CommentListHandler
import com.example.communityhub.handler.community.CommunityCreateHandler
import com.example.communityhub.handler.community.CommunityGetHandler
import com.example.communityhub.handler.community.CommunityJoinHandler
import com.example.communityhub.handler.post.PostCreateHandler
import com.example.communityhub.handler.post.PostListHandler
import com.example.communityhub.handler.upvote.UpvoteHandler
import com.example.communityhub.handler.user.*
import org.springframework.stereotype.Component

@Component
data class Handlers(
	val userHandlers: UserHandlers,
	val communityHandlers: CommunityHandlers,
	val postHandlers: PostHandlers,
	val commentHandlers: CommentHandlers,
	val upvoteHandlers: UpvoteHandlers,
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
	val communityJoinHandler: CommunityJoinHandler,
)

@Component
data class PostHandlers(
	val postCreateHandler: PostCreateHandler,
	val postListHandler: PostListHandler,
)

@Component
data class CommentHandlers(
	val commentCreateHandler: CommentCreateHandler,
	val commentListHandler: CommentListHandler,
	val commentGetHandler: CommentGetHandler,
)

@Component
data class UpvoteHandlers(
	val upvoteHandler: UpvoteHandler,
)