package profile_microservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UserProfile (
        @Id var id: Long,
        var username: String,
        var firstName: String,
        var secondName: String,
        var fullName: String = "$firstName $secondName", // this field is for faster search only
        var emailAddress: String,
        var rating: Double = 0.0,
        var description: String = "",
        var walletAddress: String = "",
        var location: String = "",
        var profilePicturePath: String = ""
        )
