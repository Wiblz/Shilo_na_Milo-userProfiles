package profile_microservice

import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path=["/profiles"])
class Controller {
    @Autowired
    lateinit var userProfileRepository: UserProfileRepository
    // TODO: place an actual microservice address
    val client = WebClient.create("https://9ef84859-75bd-434b-9e49-f6a888a6e146.mock.pstmn.io")

    @GetMapping(path=["/get"])
    fun getProfile(@RequestParam("id") id: String) : Mono<UserProfile> {
        return userProfileRepository.findById(id)
    }

    @GetMapping(path=["/search"])
    fun searchProfiles(@RequestParam("query") query: String) : Publisher<UserProfile> {
        val query = query.trim()
        val words = query.split(" ")
        val result : Publisher<UserProfile>

        result = when (words.size) {
            1 -> userProfileRepository.findOneByUsername(words[0])
            2 -> userProfileRepository.findByFullName(query)
            else -> Flux.empty()
        }

        return Flux.fromIterable(words)
                   .flatMap(userProfileRepository::findByFullNameLike)
                   .mergeWith(result)
                   .distinct()
    }

    @GetMapping(path=["/generate_wallet"])
    fun generateWalletAddress(@RequestParam("id") id: String) : Mono<String> {
        return client.get()
                     .uri("/generate")
                     .retrieve()
                     .bodyToMono(String::class.java)
                     .flatMap { address ->
                         userProfileRepository.findById(id)
                                              .map { it.walletAddress = address; it }
                                              .flatMap { userProfileRepository.save(it) }
                                              .map { it.walletAddress }
                     }
    }

    @GetMapping(path=["/wallet"])
    fun getWallet(@RequestParam("id") id: String) : Mono<String> {
        return Mono.just(id).flatMap(userProfileRepository::findById)
                            .map { it.walletAddress }
                            .defaultIfEmpty("null")
    }

    @PostMapping(path=["/update"], consumes=["application/json"])
    fun updateSingleProfile(@RequestBody userProfile: UserProfile) : Mono<Void> {
        return userProfileRepository.save(userProfile)
                                    .then()
    }

    @PostMapping(path=["/batch_update"], consumes=["application/json"])
    fun updateProfiles(@RequestBody userProfiles: Publisher<UserProfile>) : Mono<Void> {
        return userProfileRepository.saveAll(userProfiles)
                                    .then()
    }
}
