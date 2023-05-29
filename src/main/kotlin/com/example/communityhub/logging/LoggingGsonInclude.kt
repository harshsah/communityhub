package com.example.communityhub.logging

/**
 * Custom annotation to mark a field/method for serialization
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(
	AnnotationTarget.FIELD,
	AnnotationTarget.FUNCTION,
	AnnotationTarget.PROPERTY_GETTER,
)
annotation class LoggingGsonInclude
