package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = DaoConstant.COLLECTION_NAME_USER_INFO)
data class UserInfo(
	@Id
	val id: String?,
	val name: String,
	val email: String,
	val password: String,
	val created: Long,
	val updated: Long,
)