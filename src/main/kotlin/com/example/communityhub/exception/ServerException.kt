package com.example.communityhub.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

class ServerException(
	val httpStatusCode: HttpStatusCode,
	val clientMessage: String? = null,
	override val message: String? = null,
	override val cause: Throwable? = null,
):Exception(message, cause)

fun badRequestException(
	clientMessage: String? = null,
	message: String? = null,
	cause: Throwable? = null,
) = ServerException(
	httpStatusCode = HttpStatus.BAD_REQUEST,
	clientMessage = clientMessage,
	message = message ?: clientMessage,
	cause = cause,
)

fun internalServerErrorException(
	clientMessage: String? = null,
	message: String? = null,
	cause: Throwable? = null,
) = ServerException(
	httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR,
	clientMessage = clientMessage,
	message = message ?: clientMessage,
	cause = cause,
)