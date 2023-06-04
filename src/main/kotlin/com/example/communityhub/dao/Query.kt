package com.example.communityhub.dao

import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Update


interface Query<T, V> {
	fun copy(): Query<T, V>
	fun `is`(field: String?, value: Any?): Query<T, V>
	fun isNot(field: String?, value: Any?): Query<T, V>
	fun `in`(field: String?, value: List<Any>?): Query<T, V>
	fun caseSensitive(field: String?, value: String?): Query<T, V>
	fun caseInSensitive(field: String?, value: String?): Query<T, V>
	fun lte(field: String?, value: Long?): Query<T, V>
	fun gte(field: String?, value: Long?): Query<T, V>

	fun skip(skip: Long): Query<T, V>
	fun limit(limit: Int): Query<T, V>

	/**
	 * @param value: 1 or -1
	 */
	fun sort(field: String?, value: Byte): Query<T, V>
	suspend fun findOne(): V?
	suspend fun findAll(): List<V>
	suspend fun count(): Long
	suspend fun updateOne(updateMap: Map<String, Any?>): Boolean
	suspend fun deleteOne(): Boolean
	suspend fun deleteAll(): Boolean

}

internal class QueryImpl<T, V>(

	private val mongoTemplate: MongoTemplate,
	private val clazz: Class<V>,
	private val defaultLimit: Int = 100,

	private val isMap: MutableMap<String, Any?> = HashMap(),
	private val isNotMap: MutableMap<String, Any?> = HashMap(),
	private val inMap: MutableMap<String, List<Any?>> = HashMap(),
	private val caseSensitiveMap: MutableMap<String, String> = HashMap(),
	private val caseInsensitiveMap: MutableMap<String, String> = HashMap(),
	private val lteMap: MutableMap<String, Long> = HashMap(),
	private val gteMap: MutableMap<String, Long> = HashMap(),

	private var skip: Long = 0L,
	private var limit: Int = defaultLimit,

	private val sortMap: MutableMap<String, Sort.Direction> = HashMap()

) : Query<T, V> {

	override fun copy(): Query<T, V> = this.copy()
	override fun `is`(field: String?, value: Any?): Query<T, V> {
		if (!field.isNullOrEmpty()) {
			isMap[field] = value
		}
		return this
	}

	override fun isNot(field: String?, value: Any?): Query<T, V> {
		if (!field.isNullOrEmpty()) {
			isNotMap[field] = value
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

	override suspend fun findOne(): V? = mongoTemplate.findOne(generateQuery(), clazz)
	override suspend fun findAll(): List<V> = mongoTemplate.find(generateQuery(), clazz)
	override suspend fun count(): Long = mongoTemplate.count(generateQuery(
		useSort = false,
		useSkipLimit = false
	), clazz)

	override suspend fun updateOne(updateMap: Map<String, Any?>): Boolean {
		if (updateMap.isEmpty()) {
			return false
		}
		val query = generateQuery()
		val update = generateUpdateMap(updateMap)
		val updateResult = mongoTemplate.updateFirst(query, update, clazz)
		return updateResult.modifiedCount > 0
	}

	override suspend fun deleteOne(): Boolean {
		val query = generateQuery()
		query.limit(1)
		val remove = mongoTemplate.findAndRemove(query, clazz)
		return remove != null
	}

	override suspend fun deleteAll(): Boolean {
		val query = generateQuery(useSort = true, useSkipLimit = true)
		val deleteResult = mongoTemplate.remove(query, clazz)
		return deleteResult.deletedCount > 0
	}

	private fun generateUpdateMap(updateMap: Map<String, Any?>): Update {
		val update = Update()
		updateMap.forEach {(k,v) -> update[k] = v}
		return update
	}

	private fun generateQuery(
		useSort: Boolean = true,
		useSkipLimit: Boolean = true,
	): org.springframework.data.mongodb.core.query.Query {
		val query = org.springframework.data.mongodb.core.query.Query()

		isMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).`is`(v)) }
		isNotMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).ne(v)) }
		inMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).`in`(v)) }
		caseSensitiveMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).regex(v)) }
		caseSensitiveMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).regex(v, "i")) }
		lteMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).lte(v)) }
		gteMap.forEach { (k, v) -> query.addCriteria(Criteria.where(k).gte(v)) }

		if (useSort) {
			sortMap.forEach { (k, v) -> query.with(Sort.by(v, k)) }
		}
		if (useSkipLimit) {
			query.skip(skip).limit(limit)
		}

		return query
	}

}