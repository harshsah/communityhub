package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository : MongoRepository<Post, String>