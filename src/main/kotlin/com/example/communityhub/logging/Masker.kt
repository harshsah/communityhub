package com.example.communityhub.logging

import com.example.communityhub.logging.LoggingConfig.Companion.encrypt
import com.example.communityhub.logging.LoggingConfig.Companion.hash
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

/**
 * A functional interface for masking a [JsonElement]
 */
fun interface Masker {
	/**
	 * @param jsonElement to be masked
	 * @return masked JsonElement
	 */
	fun mask(jsonElement: JsonElement): JsonElement

}

fun hashEncryptMasker() = Masker {
	val string = it.toString()
	val encryptedString = encrypt(string)
	val hashedString = hash(string)
	JsonPrimitive("$hashedString - $encryptedString")
}