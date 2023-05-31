package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.Upvote
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UpvoteRepository : MongoRepository<Upvote, String>
