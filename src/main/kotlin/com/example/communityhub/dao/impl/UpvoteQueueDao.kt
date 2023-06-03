package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.UpvoteQueue
import com.example.communityhub.dao.repository.UpvoteQueueRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class UpvoteQueueDao(
	upvoteQueueRepository: UpvoteQueueRepository,
	mongoTemplate: MongoTemplate,
) : AbsDao<String, UpvoteQueue>(
	repository = upvoteQueueRepository,
	mongoTemplate = mongoTemplate,
	clazz = UpvoteQueue::class.java,
)