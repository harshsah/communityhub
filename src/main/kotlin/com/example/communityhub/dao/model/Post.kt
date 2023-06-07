package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_POST)
data class Post (
	@Id @Field(ID) val id: String,
	@Field(TITLE) val title: String,
	@Field(CONTENT) val content: String,
	@Field(USER_ID) val userId: String,
	@Field(COMMUNITY_ID) val communityId: String,
	@Field(UPVOTE) val upvote: Long = 0L,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
) {
	companion object {
		private const val ID = "_id"
		private const val TITLE = "title"
		private const val CONTENT = "content"
		const val USER_ID = "userId"
		const val COMMUNITY_ID = "communityId"
		private const val UPVOTE = "upvote"
		private const val CREATED = "created"
		private const val UPDATED = "updated"

		val VALID_SORT_PROPERTIES = listOf(CREATED, UPVOTE)
		const val DEFAULT_SORT_PROPERTY = CREATED

	}
}