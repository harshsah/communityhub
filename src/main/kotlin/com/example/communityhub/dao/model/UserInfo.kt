package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_USER_INFO)
data class UserInfo(
	@Id @Field(ID) val id: String,
	@Field(PASSWORD) val password: String,
	@Field(USER_INFO_DATA) val userInfoData: UserInfoData?,
	@Field(USER_INFO_CONTACT) val userInfoContact: UserInfoContact?,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
) {
	companion object {
		const val ID = "_id"
		private const val PASSWORD = "password"
		const val USER_INFO_DATA = "userInfoData"
		private const val USER_INFO_CONTACT = "userInfoContact"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}

data class UserInfoData(
	@Field(NAME) val name: String? = null,
	@Field(ICON_IMAGE) val iconImage: String? = null,
) {
	companion object {
		const val NAME = "name"
		const val ICON_IMAGE = "iconImage"
	}
}

data class UserInfoContact(
	@Field(PHONE) val phone: String? = null,
	@Field(EMAIL) val email: String? = null,
) {
	companion object {
		private const val PHONE = "phone"
		private const val EMAIL = "email"
	}
}