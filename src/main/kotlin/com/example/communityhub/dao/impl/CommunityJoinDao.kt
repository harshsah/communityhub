package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.CommunityJoin
import com.example.communityhub.dao.repository.CommunityJoinRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class CommunityJoinDao(
	communityJoinRepository: CommunityJoinRepository,
	mongoTemplate: MongoTemplate,
) : AbsDao<String, CommunityJoin>(
	repository = communityJoinRepository,
	mongoTemplate = mongoTemplate,
	clazz = CommunityJoin::class.java,
)