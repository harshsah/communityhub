package com.example.communityhub.logging

import org.springframework.util.ObjectUtils
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.*

object MaskFieldFactory {

	private val ignoreFieldClassMap: MutableMap<Class<*>, List<MaskField>> = HashMap()

	@JvmStatic
	fun responseEntityMaskFieldWithBody(o: Any?, extraMaskFields: List<MaskField>): List<MaskField> {
		if (o == null) {
			return listOf()
		}
		val bodyMaskFields = MaskFieldFactory[o.javaClass] + extraMaskFields
		return listOf(MaskField(name = "body", policy = MaskingPolicy.NONE, children = bodyMaskFields))
	}

	/**
	 * The result is generated using the [LoggingMask] annotations
	 * annotated to the class and it's fields. If the result is not present
	 * then the result is generated and stored for next time.
	 *
	 * @param clazz [Class] type
	 * @return List of [MaskField] for the class
	 */
	@JvmStatic
	operator fun get(clazz: Class<*>): List<MaskField> {
		if (ignoreFieldClassMap.containsKey(clazz)) {
			return ignoreFieldClassMap[clazz] ?: listOf()
		}
		// list from class
		val maskFieldsFromClassAnnotation = getMaskFieldsFromClassAnnotation(clazz)
		// mask fields from fields
		val maskFieldsFromFieldAnnotation = getMaskFieldsFromFieldAnnotation(clazz)
		val maskFields = maskFieldsFromClassAnnotation + maskFieldsFromFieldAnnotation
		ignoreFieldClassMap[clazz] = maskFields
		return maskFields
	}

	private fun getMaskFieldsFromClassAnnotation(clazz: Class<*>): List<MaskField> {
		val loggingMasks = clazz.getAnnotationsByType(LoggingMask::class.java)
		return loggingMasks.map { MaskField(
			name = it.name,
			policy = it.policy,
		) }
	}

	private fun getMaskFieldsFromFieldAnnotation(clazz: Class<*>): List<MaskField> {
		val validFields = getValidFields(clazz)
		val loggingMaskList = validFields.flatMap {
			it.getAnnotationsByType(LoggingMask::class.java)
				.map { o -> getMaskField(it, o) }
		}
		val loggingMaskInternalList = validFields.flatMap {
			it.getAnnotationsByType(LoggingMaskInterior::class.java)
				.mapNotNull { o -> getMaskField(it, o) }
		}
		return loggingMaskList + loggingMaskInternalList
	}

	private fun getMaskField(field: Field, loggingMaskInterior: LoggingMaskInterior): MaskField? {
		val level = loggingMaskInterior.level
		val classes = loggingMaskInterior.classes
		if (classes.isEmpty() || level < 0) {
			return null
		}
		val maskFields = classes.flatMap { MaskFieldFactory[it.java] }
		return MaskField(
			name = getName(loggingMaskInterior, field),
			policy = MaskingPolicy.NONE,
			level = level,
			children = maskFields
		)
	}

	private fun getMaskField(field: Field, loggingMask: LoggingMask): MaskField {
		val maskingPolicy = loggingMask.policy
		// create child list
		val childMaskFields: List<MaskField> = if (maskingPolicy == MaskingPolicy.NONE) {
			MaskFieldFactory[field.javaClass]
		} else {
			listOf()
		}
		// get list for all list
		val maskingName = getName(loggingMask, field)
        return MaskField(
	        name = maskingName,
	        policy = maskingPolicy,
	        children = childMaskFields
        )
	}

	private fun getName(loggingMask: LoggingMask, field: Field): String {
		return if (ObjectUtils.isEmpty(loggingMask.name)) {
			field.name
		} else loggingMask.name
	}

	private fun getName(loggingMaskInterior: LoggingMaskInterior, field: Field): String {
		return if (ObjectUtils.isEmpty(loggingMaskInterior.name)) {
			field.name
		} else loggingMaskInterior.name
	}

	private fun getValidFields(clazz: Class<*>?): List<Field> {
		if (clazz == null || clazz == Any::class.java) {
			return listOf()
		}
		val declaredFields = clazz.declaredFields
			.filterNot { Modifier.isStatic(it.modifiers) }
		val superClassFields = getValidFields(clazz.superclass)
		return declaredFields + superClassFields
	}
}
