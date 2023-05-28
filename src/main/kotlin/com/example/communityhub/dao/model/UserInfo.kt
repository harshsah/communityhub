package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_USER_INFO)
data class UserInfo(
	@Id @Field(ID) val id: String,
	@Field(NAME) val name: String,
	@Field(PASSWORD) val password: String,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
) {
	companion object {
		private const val ID = "_id"
		private const val NAME = "name"
		private const val PASSWORD = "password"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}