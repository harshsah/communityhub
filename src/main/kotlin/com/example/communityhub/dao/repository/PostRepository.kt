package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : MongoRepository<Post, String>