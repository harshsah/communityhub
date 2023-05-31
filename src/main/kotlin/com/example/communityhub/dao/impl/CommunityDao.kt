package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.Community
import com.example.communityhub.dao.repository.CommunityRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class CommunityDao(
	repository: CommunityRepository,
	mongoTemplate: MongoTemplate,
) : AbsDao<String, Community>(
	repository = repository,
	mongoTemplate = mongoTemplate,
	clazz = Community::class.java,
)