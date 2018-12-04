package profile_microservice.config

import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import profile_microservice.UserProfileRepository

@Configuration
@EnableReactiveMongoRepositories(
        basePackageClasses=[UserProfileRepository::class])
class MongoConfig(private val environment: Environment) : AbstractReactiveMongoConfiguration() {
    override fun getDatabaseName() = "shilo_na_milo"

    override fun reactiveMongoClient() = mongoClient()

    @Bean
    fun mongoClient() = MongoClients.create("mongodb://${environment["spring.data.mongodb.username"]}:" +
                                                      "${environment["spring.data.mongodb.password"]}@" +
                                                      "${environment["spring.data.mongodb.host"]}:" +
                                                      "${environment["spring.data.mongodb.port"]}")

    @Bean
    override fun reactiveMongoTemplate()
            = ReactiveMongoTemplate(mongoClient(), databaseName)
}