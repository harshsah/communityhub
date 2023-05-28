package com.example.communityhub.utils

import com.example.communityhub.exception.ServerException
import com.example.communityhub.exception.unauthorizedException
import com.example.communityhub.service.JwtSessionManagerFactory
import com.example.communityhub.service.UserToken
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class HeaderUtils (
	private val jwtSessionManagerFactory: JwtSessionManagerFactory
){

	@Throws(ServerException::class)
	fun verifyToken(httpHeaders: HttpHeaders): UserToken {
		val authorizationHeader = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION)
		if (authorizationHeader.isNullOrEmpty() || !authorizationHeader.startsWith("Bearer")) {
			throw unauthorizedException()
		}
		val token = authorizationHeader.substring(7)
		return jwtSessionManagerFactory.userRefreshJwtSessionManager.verifyToken(token)
	}

	fun getHeaders(servletRequest: HttpServletRequest): HttpHeaders {
		val httpHeaders = HttpHeaders()
		val headerNames = servletRequest.headerNames
		for (headerName in headerNames) {
			httpHeaders[headerName] = servletRequest.getHeaders(headerName).toList()
		}
		return httpHeaders
	}
}