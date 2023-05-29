package com.example.communityhub.logging

/**
 * Custom annotation to mark a field/method ignorable for serialization
 * when [MaskFieldFactory.get] is called. Should be used to
 * avoid circular dependent classes
 *
 *
 * The [Object.toString] method should be overridden to avoid
 * those fields
 *
 * @apiNote <pre>`class A {B b;}
 * class B {A a;}
 * class C { A a;}
 *
 * var objectA = new A();
 * var objectB = new B();
 * var objectC = new C();
 *
 * objectA.setB(objectB);
 * objectB.setA(objectA);
 * objectC.setA(objectA);
 *
 * // may cause java.lang.StackOverflowError
 * objectB.toString();
 * MaskingUtils.maskAsJson(objectB);
 *
 * // won't cause java.lang.StackOverflowError
 * MaskingUtils.markAsJson(objectC);
`</pre> *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(
	AnnotationTarget.FIELD,
	AnnotationTarget.FUNCTION,
	AnnotationTarget.PROPERTY_GETTER,
)
annotation class LoggingGsonExclude
