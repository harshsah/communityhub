package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.MessageGroup
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageGroupRepository : MongoRepository<MessageGroup, String>