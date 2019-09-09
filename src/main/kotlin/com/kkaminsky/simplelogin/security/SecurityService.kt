package com.kkaminsky.simplelogin.security

import com.kkaminsky.simplelogin.dto.UserCheckDto
import com.kkaminsky.simplelogin.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component("securityService")
class SecurityService @Autowired constructor(
        val signatureService: SignatureService,
        val userRepository: UserRepository
) {

    fun hasPermission(userCheck: UserCheckDto): Boolean {
        if(!signatureService.verify(userCheck.username+userCheck.userRole,userCheck.signature)){
            return false
        }
        else{

            val userRep = userRepository.findByUsername(userCheck.username)?:return false

            return userRep.role!!.role == userCheck.userRole

        }
    }

}