package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_COMMUNITY)
data class Community(
	@Id @Field(ID) val id: String,
	@Field(NAME) val name: String,
	@Field(ICON_IMAGE) val iconImage: String?,
	@Field(DESCRIPTION) val description: String,
	@Field(TABS) val tabs: Map<String, String>,
	@Field(MODS) val mods: List<String>,
	@Field(CREATED_BY) val createdBy: String,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
) {
	companion object {
		private const val ID = "_id"
		private const val NAME = "name"
		private const val ICON_IMAGE = "iconImage"
		private const val DESCRIPTION = "description"
		private const val TABS = "tabs"
		private const val MODS = "mods"
		private const val CREATED_BY = "createdBy"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}