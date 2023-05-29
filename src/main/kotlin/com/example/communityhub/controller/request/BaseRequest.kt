package com.example.communityhub.controller.request

import com.example.communityhub.logging.LoggingGsonInclude
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders

open class BaseRequest (
	@JsonIgnore
	@LoggingGsonInclude
	open val httpHeaders: HttpHeaders = HttpHeaders()
)