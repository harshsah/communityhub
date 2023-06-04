package com.example.communityhub.dao.repository

import com.example.communityhub.dao.model.CommunityJoin
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommunityJoinRepository : MongoRepository<CommunityJoin, String>