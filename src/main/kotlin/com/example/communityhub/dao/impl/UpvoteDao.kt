package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.Upvote
import com.example.communityhub.dao.repository.UpvoteRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class UpvoteDao(
	repository: UpvoteRepository,
	mongoTemplate: MongoTemplate,
) : AbsDao<String, Upvote> (
	repository = repository,
	mongoTemplate = mongoTemplate,
	clazz = Upvote::class.java,
)