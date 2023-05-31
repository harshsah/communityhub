package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.Comment
import com.example.communityhub.dao.repository.CommentRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class CommentDao(
	repository: CommentRepository,
	mongoTemplate: MongoTemplate
) : AbsDao<String, Comment>(
	repository = repository,
	mongoTemplate = mongoTemplate,
	clazz = Comment::class.java
)