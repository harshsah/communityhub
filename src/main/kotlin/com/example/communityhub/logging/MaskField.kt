package com.example.communityhub.logging

/**
 *
 * if [MaskField.level] >= 0 then [children]
 * at [MaskField.level] will be used for masking
 *
 * @param level    level at which masking is needed
 * @param name     name of the field
 * @param policy   [MaskingPolicy] for the field
 * @param children [List] of [MaskField] as children
 */
data class MaskField(
	val level: Int = 0,
	val name: String,
	val policy: MaskingPolicy = MaskingPolicy.DEFAULT,
	val children: List<MaskField> = listOf()
) {
	fun withLevel(i: Int): MaskField {
		return MaskField(i, name, policy, children)
	}
}