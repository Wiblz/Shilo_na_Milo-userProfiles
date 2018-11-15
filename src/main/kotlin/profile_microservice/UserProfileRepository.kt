package profile_microservice

import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserProfileRepository : ReactiveMongoRepository<UserProfile, Long> {

    fun findOneByUsername(query: String) : Mono<UserProfile>

    fun findByFullName(query: String) : Flux<UserProfile>

    fun findByFullNameLike(word: String) : Flux<UserProfile>
}
