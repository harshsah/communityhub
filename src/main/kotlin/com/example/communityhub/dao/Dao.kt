package com.example.communityhub.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.MongoRepository
import kotlin.coroutines.CoroutineContext

interface Dao<T, V> {
	fun query(): Query<T, V>
	suspend fun <S : V> save(entity: S): S
	suspend fun <S : V> saveAll(entities: MutableIterable<S>): MutableList<S>
	suspend fun findById(id: T): V?
	suspend fun existsById(id: T): Boolean
	suspend fun count(): Long
	suspend fun <S : V> insert(entities: MutableIterable<S>): MutableList<S>
	suspend fun <S : V> insert(entity: S): S
	suspend fun deleteAll(entities: MutableIterable<V>)
	suspend fun deleteAllById(ids: MutableIterable<T>)
	suspend fun delete(entity: V)
	suspend fun deleteById(id: T)
	suspend fun findAllById(ids: MutableIterable<T>): MutableList<V>

}


abstract class AbsDao<T : Any, V : Any>(
	private val repository: MongoRepository<V, T>,
	private val mongoTemplate: MongoTemplate,
	private val context: CoroutineContext = Dispatchers.IO,
	private val clazz: Class<V>,
) : Dao<T, V> {

	open override fun query(): Query<T, V> = QueryImpl(
		mongoTemplate = mongoTemplate,
		clazz = clazz,
	)

	override suspend fun <S : V> save(entity: S): S = withContext(context) {
		repository.save(entity)
	}

	override suspend fun <S : V> saveAll(entities: MutableIterable<S>): MutableList<S> = withContext(context) {
		repository.saveAll(entities)
	}

	override suspend fun findById(id: T): V? = withContext(context) {
		repository.findById(id).orElse(null)
	}

	override suspend fun existsById(id: T): Boolean = withContext(context) {
		repository.existsById(id)
	}

	override suspend fun count(): Long = withContext(context) {
		repository.count()
	}

	override suspend fun <S : V> insert(entities: MutableIterable<S>): MutableList<S> = withContext(context) {
		repository.insert(entities)
	}

	override suspend fun <S : V> insert(entity: S): S = withContext(context) {
		repository.insert(entity)
	}

	override suspend fun deleteAll(entities: MutableIterable<V>) = withContext(context) {
		repository.deleteAll(entities)
	}

	override suspend fun deleteAllById(ids: MutableIterable<T>) = withContext(context) {
		repository.deleteAllById(ids)
	}

	override suspend fun delete(entity: V) = withContext(context) {
		repository.delete(entity)
	}

	override suspend fun deleteById(id: T) = withContext(context) {
		repository.deleteById(id)
	}

	override suspend fun findAllById(ids: MutableIterable<T>): MutableList<V> = withContext(context) {
		repository.findAllById(ids)
	}

}