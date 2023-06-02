package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.Notification
import com.example.communityhub.dao.repository.NotificationRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class NotificationDao(
	notificationRepository: NotificationRepository,
	mongoTemplate: MongoTemplate,
) : AbsDao<String, Notification>(
	repository = notificationRepository,
	mongoTemplate = mongoTemplate,
	clazz = Notification::class.java,
)