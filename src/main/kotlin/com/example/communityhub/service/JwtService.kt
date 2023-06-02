package com.example.communityhub.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.example.communityhub.config.AppConfig
import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.dao.model.UserInfo
import com.example.communityhub.exception.ServerException
import com.example.communityhub.exception.internalServerErrorException
import com.example.communityhub.exception.unauthorizedException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.Instant
import java.util.*
import kotlin.jvm.Throws

abstract class JwtSessionManager<T> (
	private val ttlInMins: Long,
	private val secret: String,
	private val type: Class<T>
) {

	companion object {
		private const val CONVERT_TO_SECONDS = 60
		private val mapper = ObjectMapper().registerKotlinModule()
	}

	fun generateAuthToken(data: T): String {
		val jsonData = mapper.writeValueAsString(data)
		return JWT.create()
			.withSubject(jsonData)
			.withExpiresAt(Date.from(Instant.now().plusSeconds(ttlInMins * CONVERT_TO_SECONDS)))
			.sign(Algorithm.HMAC512(secret.toByteArray()))
	}
	@Throws(ServerException::class)

	fun verifyToken(token: String): T {
		val decodedJwt = try {
			JWT.require(Algorithm.HMAC512(secret.toByteArray())).build().verify(token)
		} catch (e: JWTVerificationException) {
			throw unauthorizedException(e)
		}
		val subject = decodedJwt.subject
		return try {
			mapper.readValue(subject, type)
		} catch (e: IOException) {
			throw internalServerErrorException(
				clientMessage = MessageConstant.SOMETHING_WENT_WRONG,
				message = "Parsing Failed",
				cause = e
			)
		}
	}
}

@Component
data class JwtSessionManagerFactory(
	val userJwtSessionManager: UserJwtSessionManager,
	val userRefreshJwtSessionManager: UserRefreshJwtSessionManager,
)

@Component
class UserJwtSessionManager(
	appConfig: AppConfig
): JwtSessionManager<UserToken>(
	appConfig.userInfoJwtConfig.ttlInMins,
	appConfig.userInfoJwtConfig.secret,
	UserToken::class.java
)

@Component
class UserRefreshJwtSessionManager(
	appConfig: AppConfig
): JwtSessionManager<UserToken>(
	appConfig.userInfoRefreshConfig.ttlInMins,
	appConfig.userInfoRefreshConfig.secret,
	UserToken::class.java
)

data class UserToken (
	val id: String
)
data class SessionInfo(
	val token: String,
	val refreshToken: String,
)

@Component
class JwtService(
	private val jwtSessionManagerFactory: JwtSessionManagerFactory
) {
	fun getSessionInfo(userInfo: UserInfo): SessionInfo {
		val userToken = UserToken(userInfo.id)
		return getSessionInfo(userToken)
	}
	fun getSessionInfo(userToken: UserToken): SessionInfo {
		val token = jwtSessionManagerFactory.userJwtSessionManager.generateAuthToken(userToken)
		val refreshToken = jwtSessionManagerFactory.userRefreshJwtSessionManager.generateAuthToken(userToken)
		return SessionInfo(token, refreshToken)
	}

	@Throws(ServerException::class)
	fun verifyToken(httpHeaders: HttpHeaders): UserToken {
		val authorizationHeader = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION)
		if (authorizationHeader.isNullOrEmpty() || !authorizationHeader.startsWith("Bearer")) {
			throw unauthorizedException()
		}
		val token = authorizationHeader.substring(7)
		return jwtSessionManagerFactory.userJwtSessionManager.verifyToken(token)
	}
}