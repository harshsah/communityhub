package com.example.communityhub.controller.request

import com.example.communityhub.logging.LoggingGsonInclude
import org.springframework.http.HttpHeaders

open class BaseRequest (
	@LoggingGsonInclude
	open val httpHeaders: HttpHeaders = HttpHeaders()
)