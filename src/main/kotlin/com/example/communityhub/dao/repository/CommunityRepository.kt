package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.Community
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommunityRepository : MongoRepository<Community, String>