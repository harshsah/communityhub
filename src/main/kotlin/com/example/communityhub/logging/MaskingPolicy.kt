package com.example.communityhub.logging

import com.google.gson.JsonNull
import com.google.gson.JsonPrimitive


enum class MaskingPolicy(val masker: Masker) {
	NONE(Masker { it }),
	IGNORE(Masker { JsonNull.INSTANCE }),
	DEFAULT(Masker { JsonPrimitive("[MASKED]") }),
	HASH_ENCRYPT(hashEncryptMasker());
}
