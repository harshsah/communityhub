package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.MessageGroup
import com.example.communityhub.dao.repository.MessageGroupRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class MessageGroupDao(
	messageGroupRepository: MessageGroupRepository,
	mongoTemplate: MongoTemplate,
) : AbsDao<String, MessageGroup>(
	repository = messageGroupRepository,
	mongoTemplate = mongoTemplate,
	clazz = MessageGroup::class.java,
)
