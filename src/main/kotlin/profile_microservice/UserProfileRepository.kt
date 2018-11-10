package profile_microservice

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository : MongoRepository<UserProfile, Long> {

    fun findOneByUsername(query: String) : UserProfile?

    fun findByFullName(query: String) : List<UserProfile>

    fun findByFullNameLike(word: String) : List<UserProfile>
}
