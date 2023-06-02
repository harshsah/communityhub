package com.example.communityhub.exception

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

class ServerException(
	val httpStatusCode: HttpStatusCode,
	val clientMessage: String? = null,
	override val message: String? = clientMessage,
	override val cause: Throwable? = null,
):Exception(message, cause) {
	fun <T> createResponse(errorResponse: T?): ResponseEntity<T> {
		if (errorResponse is BaseResponse) {
			errorResponse.message = clientMessage
		}
		return ResponseEntity
			.status(httpStatusCode)
			.body(errorResponse)
	}
}

fun badRequestException(
	clientMessage: String? = MessageConstant.SOMETHING_WENT_WRONG,
	message: String? = clientMessage,
	cause: Throwable? = null,
) = ServerException(
	httpStatusCode = HttpStatus.BAD_REQUEST,
	clientMessage = clientMessage,
	message = message,
	cause = cause,
)

fun unauthorizedException(
	cause: Throwable? = null
) = ServerException(
	httpStatusCode = HttpStatus.UNAUTHORIZED,
	clientMessage = MessageConstant.UNAUTHORIZED,
	cause = cause
)

fun internalServerErrorException(
	clientMessage: String? = MessageConstant.SOMETHING_WENT_WRONG,
	message: String? = clientMessage,
	cause: Throwable? = null,
) = ServerException(
	httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR,
	clientMessage = clientMessage,
	message = message,
	cause = cause,
)

fun createServerException(e: Throwable) = if (e is ServerException) e else internalServerErrorException(
	message = e.message,
	cause = e.cause
)
