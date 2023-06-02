package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_NOTIFICATION)
data class Notification(
	@Id @Field(ID) val id: String,
	@Field(USER_ID) val userId: String,
	@Field(TITLE) val title: String,
	@Field(CONTENT) val content: String,
	@Field(URL) val url: String,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
){

	companion object {
		private const val ID = "_id"
		private const val TITLE = "title"
		private const val CONTENT = "content"
		private const val USER_ID = "userId"
		private const val URL = "url"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}