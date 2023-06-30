package com.example.communityhub.exception

import com.example.communityhub.constant.MessageConstant
import com.example.communityhub.controller.response.BaseResponse
import org.slf4j.event.Level
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

class ServerException(
	val httpStatusCode: HttpStatusCode,
	val clientMessage: String? = null,
	val level: Level = Level.ERROR,
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
	level: Level? = null,
) = ServerException(
	httpStatusCode = HttpStatus.BAD_REQUEST,
	clientMessage = clientMessage,
	level = level ?: Level.ERROR,
	message = message,
	cause = cause,
)

fun unauthorizedException(
	cause: Throwable? = null,
	level: Level? = null,
) = ServerException(
	httpStatusCode = HttpStatus.UNAUTHORIZED,
	clientMessage = MessageConstant.UNAUTHORIZED,
	level = level ?: Level.ERROR,
	cause = cause
)

fun internalServerErrorException(
	clientMessage: String? = MessageConstant.SOMETHING_WENT_WRONG,
	message: String? = clientMessage,
	cause: Throwable? = null,
	level: Level? = null,
) = ServerException(
	httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR,
	clientMessage = clientMessage,
	level = level ?: Level.ERROR,
	message = message,
	cause = cause,
)

fun createServerException(e: Throwable) = if (e is ServerException) e else internalServerErrorException(
	message = e.message,
	cause = e.cause
)
