package profile_microservice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping(path=["/profiles"])
open class MicroserviceController {

    @GetMapping("/health")
    fun testHealth() = HttpStatus.OK

    @GetMapping("/liveness")
    fun testLiveness() = HttpStatus.OK
}