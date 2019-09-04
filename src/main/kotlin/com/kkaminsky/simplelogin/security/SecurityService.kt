package com.kkaminsky.simplelogin.security

import com.kkaminsky.simplelogin.dto.UserDto
import com.kkaminsky.simplelogin.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("securityService")
class SecurityService @Autowired constructor(
        val signatureService: SignatureService,
        val userRepository: UserRepository
) {

    fun hasPermission(user: UserDto): Boolean {
        if(!signatureService.verify(user.username+user.userRole,user.signature)){
            return false
        }
        else{

            val userRep = userRepository.findByUsername(user.username)?:return false

            return userRep.role!!.role == user.userRole

        }
    }

}