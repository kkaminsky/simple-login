package com.kkaminsky.simplelogin.controller

import com.kkaminsky.simplelogin.dto.LoginDto
import com.kkaminsky.simplelogin.dto.UserDto
import com.kkaminsky.simplelogin.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(
        val userService: UserService
) {

        @PostMapping("/login")
        @PreAuthorize("@securityService.hasPermission(#dto)")
        fun login(@RequestBody dto: LoginDto): UserDto {
                return userService.login(dto.username, dto.password)
        }

}