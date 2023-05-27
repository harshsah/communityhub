package com.example.communityhub.dao.impl

import com.example.communityhub.dao.Dao
import com.example.communityhub.dao.Query
import com.example.communityhub.dao.QueryImpl
import com.example.communityhub.dao.model.UserInfo
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

@Component
class UserInfoImpl(
	private val repository: MongoRepository<UserInfo, String>,
	private val mongoTemplate: MongoTemplate
): Dao<String, UserInfo> {
	override fun repository(): MongoRepository<UserInfo, String> {
		return repository;
	}
	override fun query(): Query<String, UserInfo> = QueryImpl(
		mongoTemplate = mongoTemplate,
		clazz = UserInfo::class.java
	)
}