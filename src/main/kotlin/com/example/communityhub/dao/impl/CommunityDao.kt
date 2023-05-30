package com.example.communityhub.dao.impl

import com.example.communityhub.dao.Dao
import com.example.communityhub.dao.Query
import com.example.communityhub.dao.QueryImpl
import com.example.communityhub.dao.model.Community
import com.example.communityhub.dao.repository.CommunityRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

@Component
class CommunityDao(
	private val repository: CommunityRepository,
	private val mongoTemplate: MongoTemplate,
) : Dao<String, Community> {
	override fun repository(): MongoRepository<Community, String> = repository

	override fun query(): Query<String, Community> =  QueryImpl(
		mongoTemplate = mongoTemplate,
		clazz = Community::class.java
	)


}