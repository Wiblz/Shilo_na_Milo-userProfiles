package profile_microservice

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository : MongoRepository<UserProfile, Long>