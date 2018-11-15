package profile_microservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import profile_microservice.utils.addNotNull
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path=["/profiles"])
class Controller {
    @Autowired
    lateinit var userProfileRepository: UserProfileRepository
    // TODO: place an actual microservice address
    val client = WebClient.create("https://9ef84859-75bd-434b-9e49-f6a888a6e146.mock.pstmn.io")

    @GetMapping(path=["/{id}"])
    fun getProfile(@PathVariable id:Long) : UserProfile {
        return userProfileRepository.findById(id).get()
    }

    @GetMapping(path=["/search"])
    fun searchProfiles(@RequestParam("query") query:String) : Set<UserProfile> {
        val query = query.trim()
        val words = query.split(" ")
        val result = HashSet<UserProfile>()

        when (words.size) {
            1 -> result.addNotNull(userProfileRepository.findOneByUsername(words[0]))
            2 -> result.addAll(userProfileRepository.findByFullName(query))
        }

        for (word in words) {
            result.addAll(userProfileRepository.findByFullNameLike(word))
        }

        return result
    }

    @GetMapping(path=["/generate_wallet"])
    fun generateWalletAddress(@RequestParam("id") id:Long) : Mono<String> {
        return client.get()
                     .uri("/generate")
                     .retrieve()
                     .bodyToMono(String::class.java)
                     .map { s ->
                         val user = userProfileRepository.findById_(id)
                         user?.walletAddress = s
                         userProfileRepository.save(user)
                         s
                     }
    }

    @GetMapping(path=["/wallet"])
    fun getWallet(@RequestParam("id") id:Long) : String {
        return userProfileRepository.findById_(id)?.walletAddress ?: "null"
    }

    @PostMapping(path=["/update"], consumes=["application/json"])
    fun updateSingleProfile(@RequestBody userProfile:UserProfile) {
        userProfileRepository.save(userProfile)
    }

    @PostMapping(path=["/batch_update"], consumes=["application/json"])
    fun updateProfiles(@RequestBody userProfiles:Array<UserProfile>) {
        for (profile in userProfiles) {
            userProfileRepository.save(profile)
        }
    }
}
