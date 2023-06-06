package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_COMMENT)
data class Comment(
	@Id @Field(ID) val id: String,
	@Field(CONTENT) val content: String,
	@Field(POST_ID) val postId: String,
	@Field(USER_ID) val userId: String,
	@Field(PARENT_ID) val parentId: String?, // if null then no comment parent
	@Field(UPVOTE) val upvote: Long = 0L,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
){

	companion object {
		private const val ID = "_id"
		private const val CONTENT = "content"
		const val POST_ID = "postId"
		const val USER_ID = "userId"
		private const val PARENT_ID = "parentId"
		private const val UPVOTE = "upvote"
		private const val CREATED = "created"
		private const val UPDATED = "updated"

		val VALID_SORT_PROPERTIES = listOf(CREATED, UPDATED)
		const val DEFAULT_SORT_PROPERTY = CREATED
	}
}
