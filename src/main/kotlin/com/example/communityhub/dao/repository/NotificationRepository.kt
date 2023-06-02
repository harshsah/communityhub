package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.Notification
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : MongoRepository<Notification, String>