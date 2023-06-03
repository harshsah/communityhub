package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.UpvoteQueue
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UpvoteQueueRepository : MongoRepository<UpvoteQueue, String>