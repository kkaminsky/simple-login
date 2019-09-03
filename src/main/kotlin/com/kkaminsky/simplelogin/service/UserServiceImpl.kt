package com.kkaminsky.simplelogin.service

import com.kkaminsky.simplelogin.dto.UserDto
import com.kkaminsky.simplelogin.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
        val userRepository: UserRepository
): UserService {


    override fun login(username: String, password: String): UserDto {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}