package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_UPVOTE)
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
		const val ID = "_id"
		const val ENTITY_TYPE = "entityType"
		const val ENTITY_ID = "entityId"
		const val USER_ID = "userId"
		const val TYPE = "type"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}

enum class UpvoteType {
	UPVOTE,
	DOWNVOTE,
	NONE,
	;

	companion object {
		private val nameMap: Map<String, UpvoteType> = UpvoteType.values().associateBy { it.name }
		fun getFromName(name: String?) = name?.let { nameMap[it] }
	}
}

enum class UpvoteEntityType {
	POST,
	COMMENT,
}