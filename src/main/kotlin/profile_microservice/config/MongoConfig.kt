package profile_microservice.config

import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import profile_microservice.UserProfileRepository

@Configuration
@EnableReactiveMongoRepositories(
        basePackageClasses=[UserProfileRepository::class])
class MongoConfig : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName() = "shilo_na_milo"

    override fun reactiveMongoClient() = mongoClient()

    @Bean
    fun mongoClient() = MongoClients.create()

    @Bean
    override fun reactiveMongoTemplate()
            = ReactiveMongoTemplate(mongoClient(), databaseName)
}