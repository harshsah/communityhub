package com.example.communityhub.handler

import com.example.communityhub.exception.ServerException
import com.example.communityhub.exception.createServerException
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.http.ResponseEntity
import java.util.function.Supplier

fun interface Handler<T, V> {
	fun handle(request: T): ResponseEntity<V>
}

abstract class AbsHandler<T,V>(
	internal val apiName: String,
	private val errorResponseSupplier: Supplier<V>?,
	internal val log: Logger = LoggerFactory.getLogger(apiName),
	internal val logLevel: Level = Level.INFO,
):Handler<T,V> {

	override fun handle(request: T): ResponseEntity<V> {
		var responseEntity: ResponseEntity<V>
		var logDTO: LogDTO<T,V>
		try {
			validate(request)
			responseEntity = runBlocking { perform(request) }
			logDTO = LogDTO(request, responseEntity, null)
		} catch (e: Exception) {
			val serverException = createServerException(e)
			val errorResponse = errorResponseSupplier?.get()
			responseEntity = serverException.createResponse(errorResponse)
			logDTO = LogDTO(request, responseEntity, serverException)
		}

		logDTO.log(this)

		return responseEntity
	}
	@Throws(ServerException::class)
	abstract suspend fun perform(request: T): ResponseEntity<V>

	@Throws(ServerException::class)
	internal fun validate(request: T) {
		// optional validate function
	}

}

data class LogDTO<T,V>(
	val request: T?,
	val responseEntity: ResponseEntity<V>?,
	val exception: ServerException?
) {
	fun log(handler: AbsHandler<T, V>) {
		if (!handler.log.isEnabledForLevel(handler.logLevel)) {
			return
		}
		handler.log
			.atLevel(handler.logLevel)
			.log("""
				API Interceptor, ${handler.apiName} api
					request: $request,
					response: $responseEntity
					exceptionProduced: ${exception != null}
					exception: {}
			""".trimIndent(), exception)
	}
}