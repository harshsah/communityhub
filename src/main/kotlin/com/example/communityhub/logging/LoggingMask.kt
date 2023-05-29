package com.example.communityhub.logging

import kotlin.reflect.KProperty

/**
 * annotation to mark a type/field for masking
 * when [MaskFieldFactory.get] is called
 *
 *
 * The [Object.toString] should be overridden
 * to mask the corresponding fields
 *
 * @apiNote <pre>`class UserInfo {
 * String name;
 * String phoneNumber;
 * String email;
 * }
 * var userInfo = new UserInfo("John Doe", "+911234567890", "john.doe@mail.in");
 * log.info("{}", MaskingUtils.maskAsJson(userInfo));
 * //{"name":"John Doe","phoneNum":"[MASKED]","email":"john.doe@mail.in"}
 *
`</pre> *
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@JvmRepeatable(LoggingMasks::class)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingMask(
	/**
	 * @return Array of field names to be removed,
	 * if empty then [KProperty.name] will be used
	 */
	val name: String = "",
	/**
	 * @return MaskingPolicy for the [LoggingMask.name]
	 */
	val policy: MaskingPolicy = MaskingPolicy.DEFAULT
)
