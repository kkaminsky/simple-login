package com.kkaminsky.simplelogin.controller

import com.kkaminsky.simplelogin.dto.*
import com.kkaminsky.simplelogin.security.SecurityService
import com.kkaminsky.simplelogin.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(
        val userService: UserService,
        val securityService: SecurityService
) {

        @PostMapping("/login")
        fun login(@RequestBody dto: LoginDto): UserCheckDto {
                return userService.login(dto.username, dto.password)
        }

        @PostMapping("/register")
        fun register(@RequestBody dto: RegisterDto) {
                userService.register(dto.username, dto.password)
        }

        @PostMapping("/register/admin")
        fun registerAdmin(@RequestBody dto: RegisterDto){
                userService.registerAdmin(dto.username, dto.password)
        }


        @PostMapping("/edit")
        @PreAuthorize("@securityService.hasPermission(#dto.userCheck)")
        fun edit(@RequestBody dto: EditDto){

                userService.edit(dto.oldUsername, dto.newUsername, dto.newRole)
        }

        @PostMapping("/delete")
        @PreAuthorize("@securityService.hasPermission(#dto.userCheck)")
        fun delete(@RequestBody dto: DeleteDto){
                return userService.delete(dto.username)
        }

        @PostMapping("/all")
        @PreAuthorize("@securityService.hasPermission(#dto.userCheck)")
        fun getAllUsers(@RequestBody dto: GetAllUsersDto): List<UserDto>{
                if(!securityService.hasPermission(dto.userCheck)) throw Exception("У вас нет доступа к запршиваему ресурсу!")
                return userService.getAllUsers()
        }

}