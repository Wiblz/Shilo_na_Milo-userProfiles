package profile_microservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path=["/profiles"])
class Controller {
    @Autowired
    lateinit var userProfileRepository: UserProfileRepository

    @GetMapping(path=["/{id}"])
    fun getProfile(@PathVariable id:Long) : UserProfile {
        return userProfileRepository.findById(id).get()
    }

    @PostMapping(path=["/update"], consumes=["application/json"])
    fun updateProfile(@RequestBody userProfile:UserProfile) {
        userProfileRepository.save(userProfile)
    }
}