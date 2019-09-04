package com.kkaminsky.simplelogin.service

import com.kkaminsky.simplelogin.dto.UserDto
import com.kkaminsky.simplelogin.dto.UsernameDto

interface UserService {

    fun login(username: String, password:String):UserDto
    fun register(username: String, password: String)
    fun edit(oldUsername: String, newUsername: String)
    fun delete(username: String)
    fun getAllUsers(): List<UsernameDto>
}