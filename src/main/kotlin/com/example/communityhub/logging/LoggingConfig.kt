package com.example.communityhub.logging

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Component(LoggingConfig.LOGGING_CONFIG_BEAN_NAME)
internal class LoggingConfig internal constructor(
	@Value("\${logging.encryption.password}") password:String,
	@Value("\${logging.encryption.salt}") salt: String,
	) {
	init {
		if (encryptionPassword == null || encryptionSalt == null) {
			encryptionPassword = password
			encryptionSalt = salt
		}
	}

	private object NotInitTextEncryptor : TextEncryptor {
		override fun encrypt(text: String): String {
			return "[ENCRYPTOR_NOT_INIT]"
		}

		override fun decrypt(encryptedText: String): String {
			return "[ENCRYPTOR_NOT_INIT]"
		}
	}

	companion object {
		const val LOGGING_CONFIG_BEAN_NAME = "loggingConfig"
		private var encryptionPassword: String? = null
		private var encryptionSalt: String? = null

		@JvmStatic
		private fun encryptor() = if (encryptionPassword.isNullOrEmpty() || encryptionSalt.isNullOrEmpty()) {
				NotInitTextEncryptor
			} else {
				Encryptors.text(encryptionPassword, encryptionSalt)
			}

		@JvmStatic
		fun encrypt(input: String): String = encryptor().encrypt(input)

		@JvmStatic
		fun hash(input: String): String {
			val algo = "SHA-256"
			val instance = MessageDigest.getInstance(algo)
			// Convert the input string to bytes
			val inputBytes = input.toByteArray(StandardCharsets.UTF_8)
			// Calculate the hash value
			val hashBytes = instance.digest(inputBytes)
			// Convert the hash value to a hexadecimal string representation
			val sb = StringBuilder()
			for (hashByte in hashBytes) {
				sb.append(String.format("%02x", hashByte))
			}
			return sb.toString()
		}
	}
}
