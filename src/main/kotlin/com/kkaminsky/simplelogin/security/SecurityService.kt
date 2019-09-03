package com.kkaminsky.simplelogin.security

import org.springframework.stereotype.Component

@Component("securityService")
class SecurityService {

    fun hasPermission(key: String): Boolean {
        return true
    }

}