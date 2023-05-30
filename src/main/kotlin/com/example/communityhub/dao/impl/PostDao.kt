package com.example.communityhub.dao.impl

import com.example.communityhub.dao.Dao
import com.example.communityhub.dao.Query
import com.example.communityhub.dao.QueryImpl
import com.example.communityhub.dao.model.Post
import com.example.communityhub.dao.repository.PostRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

@Component
class PostDao(
	val postRepository: PostRepository,
	val mongoTemplate: MongoTemplate,
) : Dao<String, Post> {

	override fun repository(): MongoRepository<Post, String> = postRepository

	override fun query(): Query<String, Post> = QueryImpl(
		mongoTemplate = mongoTemplate,
		clazz = Post::class.java
	)
}