package com.example.communityhub.dao.model

import com.example.communityhub.constant.DaoConstant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = DaoConstant.COLLECTION_NAME_COMMUNITY_JOIN)
data class CommunityJoin(
	@Id @Field(ID) val id: String,
	@Field(USER_ID) val userId: String,
	@Field(COMMUNITY_ID) val communityId: String,
	@Field(CREATED) val created: Long,
	@Field(UPDATED) val updated: Long,
) {
	companion object {
		private const val ID = "_id"
		private const val USER_ID = "userId"
		private const val COMMUNITY_ID = "communityId"
		private const val CREATED = "created"
		private const val UPDATED = "updated"
	}
}