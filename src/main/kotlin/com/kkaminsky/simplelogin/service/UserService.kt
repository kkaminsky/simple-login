package com.kkaminsky.simplelogin.service

import com.kkaminsky.simplelogin.dto.UserDto

interface UserService {

    fun login(username: String, password:String):UserDto
}