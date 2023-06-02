package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.Message
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : MongoRepository<Message, String>