package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.Post
import com.example.communityhub.dao.repository.PostRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class PostDao(
	postRepository: PostRepository,
	mongoTemplate: MongoTemplate,
) : AbsDao<String, Post>(
	repository = postRepository,
	mongoTemplate = mongoTemplate,
	clazz = Post::class.java,
)