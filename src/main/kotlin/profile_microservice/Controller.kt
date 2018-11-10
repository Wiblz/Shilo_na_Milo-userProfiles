package profile_microservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import profile_microservice.utils.addNotNull

@RestController
@RequestMapping(path=["/profiles"])
class Controller {
    @Autowired
    lateinit var userProfileRepository: UserProfileRepository

    @GetMapping(path=["/{id}"])
    fun getProfile(@PathVariable id:Long) : UserProfile {
        return userProfileRepository.findById(id).get()
    }

    @GetMapping(path=["/search"])
    fun searchProfiles(@RequestParam("query") query:String) : Set<UserProfile> {
        val query = query.trim()
        val words = query.split(" ")
        val result = HashSet<UserProfile>()

        when {
            words.size == 1 -> result.addNotNull(userProfileRepository.findOneByUsername(words[0]))
            words.size == 2 -> result.addAll(userProfileRepository.findByFullName(query))
        }

        for (word in words) {
            result.addAll(userProfileRepository.findByFullNameLike(word))
        }

        return result
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
