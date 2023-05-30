package com.example.communityhub.utils

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders

object HeaderUtils {

	fun getHeaders(servletRequest: HttpServletRequest): HttpHeaders {
		val httpHeaders = HttpHeaders()
		val headerNames = servletRequest.headerNames
		for (headerName in headerNames) {
			httpHeaders[headerName] = servletRequest.getHeaders(headerName).toList()
		}
		return httpHeaders
	}
}