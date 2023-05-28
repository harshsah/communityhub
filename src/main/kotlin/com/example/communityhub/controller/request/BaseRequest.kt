package com.example.communityhub.controller.request

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpHeaders

open class BaseRequest (
	@JsonIgnore
	open val httpHeaders: HttpHeaders
)