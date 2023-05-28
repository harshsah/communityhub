package com.example.communityhub.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
data class AppConfig(
	val userInfoJwtConfig: UserInfoJwtConfig,
	val userInfoRefreshConfig: UserInfoRefreshJwtConfig
)



open class JwtConfig (
	val ttlInMins: Long,
	val secret: String,
)

@Component class UserInfoJwtConfig(
	@Value("\${jwt.userInfo.ttlInMins}")
	ttlInMins: Long,
	@Value("\${jwt.userInfo.secret}")
	secret: String): JwtConfig(ttlInMins, secret)

@Component class UserInfoRefreshJwtConfig(
	@Value("\${jwt.userInfo.refresh.ttlInMins}")
	ttlInMins: Long,
	@Value("\${jwt.userInfo.refresh.secret}")
	secret: String): JwtConfig(ttlInMins, secret)