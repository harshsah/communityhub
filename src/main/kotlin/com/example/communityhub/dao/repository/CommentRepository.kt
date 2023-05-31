package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.Comment
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : MongoRepository<Comment, String>