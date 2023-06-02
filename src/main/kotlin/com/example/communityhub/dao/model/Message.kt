package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_MESSAGE)
data class Message (
	@Id @Field(ID) val id: String,
	@Field(CONTENT) val content: String,
	@Field(FROM_ID_TYPE) val fromIdType: MessageFromIdType,
	@Field(FROM_ID) val fromId: String,
	@Field(TO_ID_TYPE) val toIdType: MessageToIdType,
	@Field(TO_ID) val toId: String,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
) {

	companion object {
		private const val ID = "_id"
		private const val CONTENT = "content"
		private const val FROM_ID_TYPE = "fromIdType"
		private const val FROM_ID = "fromId"
		private const val TO_ID_TYPE = "toIdType"
		private const val TO_ID = "toId"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}

enum class MessageFromIdType {
	USER,
	COMMUNITY,
}

enum class MessageToIdType {
	USER,
	GROUP,
}