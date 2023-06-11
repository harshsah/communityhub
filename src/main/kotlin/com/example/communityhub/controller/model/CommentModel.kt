package com.example.communityhub.controller.model

import com.example.communityhub.dao.model.Comment

data class CommentModel(
	val id: String,
	val content: String,
	val userId: String,
	val postId: String,
	val communityId: String,
	val parentId: String?,
	val upvote: Long,
)

fun Comment.toModel() =  CommentModel(
	id = this.id,
	content = this.content,
	userId = this.userId,
	postId = this.postId,
	communityId = this.communityId,
	parentId = this.postId,
	upvote = this.upvote,
)
