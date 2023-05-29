package com.example.communityhub.logging

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.*
import org.slf4j.LoggerFactory

object MaskingUtils {

	private val log = LoggerFactory.getLogger(MaskingUtils::class.java)

	private val GSON =  Gson().newBuilder()
		.setExclusionStrategies(LoggingExclusionStrategy()).create()
     fun maskAsJson(o:Any?): String? {
        return maskAsJsonWithExtraMasks(o, listOf())
    }

     fun maskAsJsonWithExtraMasks(o:Any?, extraMaskFields:List<MaskField>): String? {
        return if (o == null) {
			null
		} else try  {
			val maskFields = MaskFieldFactory[o.javaClass] + extraMaskFields
	        maskAsJson(o, maskFields)
        } catch (e: Exception) {
	        log.error("unable to fetch maskFields {}", o.javaClass, e)
	        o.toString()
        }
     }

	fun maskAsJson(o: Any?, maskFields: List<MaskField>): String? {
		return if (o == null) {
			null
		} else try {
			val jsonElement: JsonElement = GSON.toJsonTree(o)
			mask(jsonElement, maskFields).toString()
		} catch (e: Exception) {
			log.error("unable to serialize {}", o.javaClass, e)
			o.toString()
		}
	}

	fun maskAsJson(string: String, maskFields: List<MaskField>): String {
		return if (maskFields.isEmpty()) {
			string
		} else try {
			val jsonElement: JsonElement = GSON.fromJson(string, JsonElement::class.java)
			mask(jsonElement, maskFields).toString()
		} catch (e: Exception) {
			log.error("unable to mask with maskFields {}", maskFields, e)
			"[MASKED]"
		}
	}

	private fun mask(jsonElement: JsonElement, maskFields: List<MaskField>): JsonElement {
		when (jsonElement) {
			is JsonArray -> jsonElement.forEach { mask(it, maskFields) }
			is JsonObject -> maskFields.forEach { mask(jsonElement, it) }
		}
		return jsonElement
	}

	private fun mask(jsonObject: JsonObject, maskField: MaskField) {
		val policy = maskField.policy
		val name = maskField.name
		val level: Int = maskField.level
		val jsonElement = jsonObject[name] ?: return

		if (level > 0) {
			val childJsonObjects = getChildJsonObjects(jsonElement, level)
			val maskFieldsWithLevelZero = maskField.children
			childJsonObjects.forEach { mask(it, maskFieldsWithLevelZero) }
			return
		}
		if (policy != MaskingPolicy.NONE) {
			jsonObject.add(name, policy.masker.mask(jsonElement))
			return
		}
		mask(jsonElement, maskField.children)
	}

	private fun getChildJsonObjects(jsonElement: JsonElement, level: Int): List<JsonObject> {
		return if (level <= 0) {
			when (jsonElement) {
				is JsonArray -> jsonElement.filterIsInstance<JsonObject>()
				is JsonObject -> listOf(jsonElement)
				else -> listOf()
			}
        } else {
			when (jsonElement) {
				is JsonArray -> jsonElement.flatMap { getChildJsonObjects(it, level) }
				is JsonObject -> jsonElement.entrySet().map { it.value }
					.flatMap { getChildJsonObjects(it, level-1) }
				else -> listOf()
			}
		}
	}


	private class LoggingExclusionStrategy: ExclusionStrategy {
		override fun shouldSkipField(field: FieldAttributes): Boolean {
			if (field.getAnnotation(LoggingGsonInclude::class.java) != null) {
				return false
			}
			return field.getAnnotation(LoggingGsonExclude::class.java) != null
					|| field.getAnnotation(JsonIgnore::class.java) != null
		}
		override fun shouldSkipClass(clazz: Class<*>?): Boolean {
			return false
		}
	}

}
