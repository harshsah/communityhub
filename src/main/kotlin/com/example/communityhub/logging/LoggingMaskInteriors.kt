package com.example.communityhub.logging

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
annotation class LoggingMaskInteriors(vararg val value: LoggingMaskInterior)
