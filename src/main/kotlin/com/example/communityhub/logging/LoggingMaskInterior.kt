package com.example.communityhub.logging

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * annotation to mark a type/field for masking
 * when [MaskFieldFactory.get] is called
 *
 *
 * Used for properties like Collections and Maps
 *
 *
 * The [Object.toString] should be overridden
 * to mask the corresponding fields
 *
 * @apiNote <pre>`class UserInfo {
 * String name = "John Doe";
 * String phoneNumber = "+911234567890";
 * String email = "john.doe@mail.in";
 * }
 * class UserData {
 * = 1, classes = UserInfo.class)
 * Object mapAsObject;
 * = 2, classes = UserInfo.class)
 * Map<String, Map<String, UserInfo>> mapData;
 * = 0, classes = UserInfo.class)
 * List<UserInfo> listData;
 * = 1, classes = UserInfo.class)
 * List<Map<String, UserInfo>> mixedData;
 * }
 * var data = new UserData(
 * Map.of("a", new UserInfo()),
 * Map.of("a", Map.of("b", new UserInfo())),
 * List.of(new UserInfo()),
 * List.of(Map.of("a", new UserInfo()))
 * );
 * log.info("{}", MaskingUtils.maskAsJson(data));
 * //{"mapAsObject":{"a":{"name":"John Doe","phoneNumber":"\[MASKED]","email":"john.doe@mail.in"}},
 * //"mapData":{"a":{"b":{"name":"John Doe","phoneNumber":"\[MASKED]","email":"john.doe@mail.in"}}},
 * //"listData":[{"name":"John Doe","phoneNumber":"\[MASKED]","email":"john.doe@mail.in"}],
 * //"mixedData":[{"a":{"name":"John Doe","phoneNumber":"\[MASKED]","email":"john.doe@mail.in"}}]}
 * `</pre>
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@JvmRepeatable(LoggingMaskInteriors::class)
@Retention(
	AnnotationRetention.RUNTIME
)
annotation class LoggingMaskInterior(
	/**
	 * @return level value
	 */
	val level: Int = 0,
	/**
	 * @return Array of field names to be removed,
	 * if empty then [KProperty.name] will be used
	 */
	val name: String = "",
	/**
	 *
	 * @return Array of [KClass] for which the mask will
	 * be created at level [LoggingMaskInterior.level]
	 */
	val classes: Array<KClass<*>> = []
)
