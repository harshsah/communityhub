package com.example.communityhub.controller.response

import com.example.communityhub.logging.LoggingGsonInclude

open class BaseResponse(
	@LoggingGsonInclude
	open var message: String? = null
) {
}