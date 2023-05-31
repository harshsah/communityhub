package com.example.communityhub.dao.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

data class Comment(
	@Id @Field(ID) val id: String,
	@Field(CONTENT) val content: String,
	@Field(POST_ID) val postId: String,
	@Field(USER_ID) val userId: String,
	@Field(PARENT_ID) val parentId: String?, // if null then no comment parent
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
){

	companion object {
		private const val ID = "_id"
		private const val CONTENT = "content"
		private const val POST_ID = "postId"
		private const val USER_ID = "userId"
		private const val PARENT_ID = "parentId"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}