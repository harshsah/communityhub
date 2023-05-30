package com.example.communityhub.controller.model

import com.example.communityhub.dao.model.Post

data class PostModel(
	val id: String,
	val title: String,
	val content: String,
	val userId: String,
	val communityId: String,
	val created: Long,
)

fun getPostModel(post: Post) = PostModel(
	id = post.id,
	title = post.title,
	content = post.content,
	userId = post.userId,
	communityId = post.communityId,
	created = post.created,
)