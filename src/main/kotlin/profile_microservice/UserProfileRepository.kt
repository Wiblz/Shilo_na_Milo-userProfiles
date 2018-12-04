package profile_microservice

import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserProfileRepository : ReactiveMongoRepository<UserProfile, String> {

    fun findOneByUsernameIgnoreCase(query: String) : Mono<UserProfile>

    fun findByFullNameIgnoreCase(query: String) : Flux<UserProfile>

    fun findByFullNameLikeIgnoreCase(word: String) : Flux<UserProfile>

    fun deleteById_(id: String) : Mono<Long>
}
