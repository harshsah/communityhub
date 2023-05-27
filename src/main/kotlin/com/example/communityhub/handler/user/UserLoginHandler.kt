package com.example.communityhub.handler.user

import com.example.communityhub.dao.impl.UserInfoDao
import org.springframework.stereotype.Component

@Component
class UserLoginHandler(
	private val userInfoDao: UserInfoDao
){
}