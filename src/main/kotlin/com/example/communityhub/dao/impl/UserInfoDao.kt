package com.example.communityhub.dao.impl

import com.example.communityhub.dao.AbsDao
import com.example.communityhub.dao.model.UserInfo
import com.example.communityhub.dao.repository.UserInfoRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class UserInfoDao(
	repository: UserInfoRepository,
	mongoTemplate: MongoTemplate,
) : AbsDao<String, UserInfo>(
	repository = repository,
	mongoTemplate = mongoTemplate,
	clazz = UserInfo::class.java
)