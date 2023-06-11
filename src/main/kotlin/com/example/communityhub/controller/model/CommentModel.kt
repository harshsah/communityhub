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

fun getCommentModel(comment: Comment) = CommentModel(
	id = comment.id,
	content = comment.content,
	userId = comment.userId,
	postId = comment.postId,
	communityId = comment.communityId,
	parentId = comment.postId,
	upvote = comment.upvote,
)