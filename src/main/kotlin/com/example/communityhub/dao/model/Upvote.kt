package com.example.communityhub.dao.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

data class Upvote(
	@Id @Field(ID) val id: String,
	@Field(ENTITY_TYPE) val entityType: UpvoteEntityType,
	@Field(ENTITY_ID) val entityId: String,
	@Field(USER_ID) val userId: String,
	@Field(TYPE) val type: UpvoteType,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
) {

	companion object {
		private const val ID = "_id"
		private const val ENTITY_TYPE = "entityType"
		private const val ENTITY_ID = "entityId"
		private const val USER_ID = "userId"
		private const val TYPE = "type"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}

enum class UpvoteType {
	UPVOTE,
	DOWNVOTE,
	NONE,
}

enum class UpvoteEntityType {
	POST,
	COMMENT,
}