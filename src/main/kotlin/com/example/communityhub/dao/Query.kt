package com.example.communityhub.dao

import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria


interface Query<T, V> {
	fun `is`(field: String?, value: Any?): Query<T, V>
	fun `in`(field: String?, value: List<Any>?): Query<T, V>
	fun caseSensitive(field: String?, value: String?): Query<T, V>
	fun caseInSensitive(field: String?, value: String?): Query<T, V>
	fun lte(field: String?, value: Long?): Query<T, V>
	fun gte(field: String?, value: Long?): Query<T, V>

	fun skip(skip: Long): Query<T, V>
	fun limit(limit: Int): Query<T, V>

	fun copy(): Query<T, V>

	/**
	 * @param value: 1 or -1
	 */
	fun sort(field: String?, value: Byte): Query<T, V>
	fun findOne(): V?
	fun findAll(): List<V>
	fun count(): Long

}

internal class QueryImpl<T, V>(

	private val mongoTemplate: MongoTemplate,
	private val clazz: Class<V>,
	private val defaultLimit: Int = 100,

	private val isMap: MutableMap<String, Any> = HashMap(),
	private val inMap: MutableMap<String, List<Any>> = HashMap(),
	private val caseSensitiveMap: MutableMap<String, String> = HashMap(),
	private val caseInsensitiveMap: MutableMap<String, String> = HashMap(),
	private val lteMap: MutableMap<String, Long> = HashMap(),
	private val gteMap: MutableMap<String, Long> = HashMap(),

	private var skip: Long = 0L,
	private var limit: Int = defaultLimit,

	private val sortMap: MutableMap<String, Sort.Direction> = HashMap()

) : Query<T, V> {
	override fun `is`(field: String?, value: Any?): Query<T, V> {
		if (!field.isNullOrEmpty() && value != null) {
			isMap[field] = value
		}
		return this
	}

	override fun `in`(field: String?, value: List<Any>?): Query<T, V> {
		if (!field.isNullOrEmpty() && !value.isNullOrEmpty()) {
			inMap[field] = value
		}
		return this
	}

	override fun caseSensitive(field: String?, value: String?): Query<T, V> {
		if (!field.isNullOrEmpty() && value != null) {
			caseSensitiveMap[field] = value
		}
		return this
	}

	override fun caseInSensitive(field: String?, value: String?): Query<T, V> {
		if (!field.isNullOrEmpty() && value != null) {
			caseInsensitiveMap[field] = value
		}
		return this
	}

	override fun lte(field: String?, value: Long?): Query<T, V> {
		if (!field.isNullOrEmpty() && value != null) {
			lteMap[field] = value
		}
		return this
	}

	override fun gte(field: String?, value: Long?): Query<T, V> {
		if (!field.isNullOrEmpty() && value != null) {
			gteMap[field] = value
		}
		return this
	}

	override fun skip(skip: Long): Query<T, V> {
		this.skip = skip
		return this
	}

	override fun limit(limit: Int): Query<T, V> {
		if (this.limit <= defaultLimit) {
			this.limit = limit
		}
		return this
	}

	override fun sort(field: String?, value: Byte): Query<T, V> {
		if (!field.isNullOrEmpty()) {
			when (value.toInt()) {
				1 -> sortMap[field] = Sort.Direction.ASC
				2 -> sortMap[field] = Sort.Direction.DESC
			}
		}
		return this
	}

	override fun findOne(): V? = mongoTemplate.findOne(generateQuery(), clazz)
	override fun findAll(): List<V> = mongoTemplate.find(generateQuery(), clazz)
	override fun count(): Long = mongoTemplate.count(generateQuery(), clazz)
	override fun copy(): Query<T, V> = this.copy()

	private fun generateQuery(): org.springframework.data.mongodb.core.query.Query {
		val query = org.springframework.data.mongodb.core.query.Query()

		isMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).`is`(v)) }
		inMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).`in`(v)) }
		caseSensitiveMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).regex(v)) }
		caseSensitiveMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).regex(v, "i")) }
		lteMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).lte(v)) }
		gteMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).gte(v)) }

		sortMap.forEach { (k, v) -> query.with(Sort.by(v, k)) }

		query.skip(skip).limit(limit)

		return query
	}

}