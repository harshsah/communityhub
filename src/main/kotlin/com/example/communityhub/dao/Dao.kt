package com.example.communityhub.dao

import org.springframework.data.mongodb.repository.MongoRepository

interface Dao<T, V> {
	fun repository(): MongoRepository<V, T>
	fun query(): Query<T, V>
}
