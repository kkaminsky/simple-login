package com.kkaminsky.simplelogin.controller

import com.kkaminsky.simplelogin.dto.*
import com.kkaminsky.simplelogin.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(
        val userService: UserService
) {

        @PostMapping("/login")
        fun login(@RequestBody dto: LoginDto): UserDto {
                return userService.login(dto.username, dto.password)
        }

        @PostMapping("/register")
        fun register(@RequestBody dto: RegisterDto) {
                userService.register(dto.username, dto.password)
        }


        @PostMapping("/edit")
        @PreAuthorize("@securityService.hasPermission(#dto.user)")
        fun edit(@RequestBody dto: EditDto){
                userService.edit(dto.oldUsername, dto.newUsername)
        }

        @PostMapping("/delete")
        @PreAuthorize("@securityService.hasPermission(#dto.user)")
        fun delete(@RequestBody dto: DeleteDto){
                return userService.delete(dto.username)
        }

        @PostMapping("/all")
        @PreAuthorize("@securityService.hasPermission(#dto.user)")
        fun getAllUsers(@RequestBody dto: GetAllUsersDto): List<UsernameDto>{
                return userService.getAllUsers()
        }

}