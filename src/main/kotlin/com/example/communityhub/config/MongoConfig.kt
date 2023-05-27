package com.example.communityhub.config

import com.mongodb.*
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.connection.ClusterSettings
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import java.util.*

@Configuration
class MongoConfig {

	@Value("\${mongo.connection.url.list}")
	private lateinit var mongoConnectionUrlList: String

	@Value("\${mongo.database}")
	private  lateinit var  mongoDatabase: String

	@Value("\${mongo.username}")
	private  lateinit var  mongoUsername: String

	@Value("\${mongo.password}")
	private  lateinit var  mongoPassword: String

	@Bean
	fun mongoClient(): MongoClient {
		val seeds: MutableList<ServerAddress> = ArrayList()
		Arrays.stream(
			mongoConnectionUrlList.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
		).forEach { connection: String ->
			val hostAndPort =
				connection.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
			seeds.add(ServerAddress(hostAndPort[0], hostAndPort[1].toInt()))
		}
		val credential = MongoCredential.createCredential(
			mongoUsername, mongoDatabase,
			mongoPassword.toCharArray()
		)
		return if (mongoUsername.isNotEmpty()) MongoClients.create(
			MongoClientSettings.builder()
				.applyToClusterSettings { builder: ClusterSettings.Builder -> builder.hosts(seeds) }
				.credential(credential)
				.build()
		) else MongoClients.create(
			MongoClientSettings.builder()
				.applyToClusterSettings { builder: ClusterSettings.Builder -> builder.hosts(seeds) }.build()
		)
	}

	@Bean
	fun mongoDbFactory(): MongoDatabaseFactory {
		return SimpleMongoClientDatabaseFactory(mongoClient(), mongoDatabase)
	}

	@Bean(name = ["mongoTransactionManager"])
	fun transactionManager(dbFactory: MongoDatabaseFactory): MongoTransactionManager {
		val transactionOptions = TransactionOptions.builder().readConcern(ReadConcern.MAJORITY)
			.writeConcern(WriteConcern.MAJORITY).build()
		return MongoTransactionManager(dbFactory, transactionOptions)
	}
}
