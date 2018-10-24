package profile_microservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UserProfile (
        @Id var id: Long,
        var login: String,
        var rating: Double,
        var description: String,
        var walletAddress: String,
        var location: String,
        var profilePicturePath: String
        )