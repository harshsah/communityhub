package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.UserInfo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserInfoRepository: MongoRepository<UserInfo, String>