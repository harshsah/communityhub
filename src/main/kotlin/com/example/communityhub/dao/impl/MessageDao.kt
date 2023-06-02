package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.Message
import com.example.communityhub.dao.repository.MessageRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class MessageDao(
	messageRepository: MessageRepository,
	mongoTemplate: MongoTemplate,
) : AbsDao<String, Message>(
	repository = messageRepository,
	mongoTemplate = mongoTemplate,
	clazz = Message::class.java,
)