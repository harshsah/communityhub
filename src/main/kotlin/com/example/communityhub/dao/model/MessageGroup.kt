package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_MESSAGE_GROUP)
data class MessageGroup(
	@Id @Field(ID) val id: String,
	@Field(NAME) val name: String,
	@Field(MEMBERS) val members: List<String>,
	@Field(ADMINS) val admins: List<String>,
	@Field(CREATED_BY) val createdBy: String,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,

	) {
	companion object {
		private const val ID = "_id"
		private const val NAME = "name"
		private const val MEMBERS = "members"
		private const val ADMINS = "admins"
		private const val CREATED_BY = "createdBy"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}