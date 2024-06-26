package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_UPVOTE_QUEUE)
data class UpvoteQueue(
	@Id @Field(ID) val id: String,
	@Field(ENTITY_TYPE) val entityType: UpvoteEntityType,
	@Field(ENTITY_ID) val entityId: String,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
) {
	companion object {
		private const val ID = "_id"
		private const val ENTITY_TYPE = "entityType"
		private const val ENTITY_ID = "entityId"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}