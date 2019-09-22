package com.kkaminsky.simplelogin.service

import com.kkaminsky.simplelogin.dto.FileDto
import com.kkaminsky.simplelogin.dto.UserCheckDto
import com.kkaminsky.simplelogin.dto.UserDto
import com.kkaminsky.simplelogin.dto.UsernameDto

interface UserService {

    fun login(username: String, password:String):UserCheckDto
    fun register(username: String, password: String)
    fun edit(oldUsername: String, newUsername: String, newRole: String)
    fun delete(username: String)
    fun getAllUsers(): List<UserDto>
    fun registerAdmin(username: String, password: String)
    fun createUserFile(username: String, fileName: String)
    fun deleteFile(username: String, fileName: String)
    fun readFile(username: String, fileName: String): String
    fun writeFile(username: String, fileName: String, text: String)
    fun takeGrant(username: String, fileName: String, grantName: String, usernameNew: String)
    fun getFilesForUser(username: String): List<FileDto>
}