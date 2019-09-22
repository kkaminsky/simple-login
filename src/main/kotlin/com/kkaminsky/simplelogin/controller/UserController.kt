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
        fun edit(@RequestBody dto: EditDto){
                if(!securityService.hasPermission(dto.userCheck))
                        throw Exception("У вас нет доступа к запршиваему ресурсу!")
                userService.edit(dto.oldUsername, dto.newUsername, dto.newRole)
        }

        @PostMapping("/delete")
        fun delete(@RequestBody dto: DeleteDto){
                if(!securityService.hasPermission(dto.userCheck))
                        throw Exception("У вас нет доступа к запршиваему ресурсу!")
                return userService.delete(dto.username)
        }

        @PostMapping("/all")
        fun getAllUsers(@RequestBody dto: GetAllUsersDto): List<UserDto> {
                if(!securityService.hasPermission(dto.userCheck))
                        throw Exception("У вас нет доступа к запршиваему ресурсу!")
                return userService.getAllUsers()
        }

        @PostMapping("/file/create")
        fun createFile(@RequestBody dto: CreateFileDto){
                if(!securityService.hasPermission(dto.userCheck))
                        throw Exception("У вас нет доступа к запршиваему ресурсу!")
                userService.createUserFile(dto.userCheck.username,dto.fileName)
        }

        @PostMapping("/file/delete")
        fun deleteFile(@RequestBody dto: CreateFileDto){
                if(!securityService.hasPermission(dto.userCheck))
                        throw Exception("У вас нет доступа к запршиваему ресурсу!")
                userService.deleteFile(dto.userCheck.username,dto.fileName)
        }

        @PostMapping("/file/all")
        fun getAllFilesForUser(@RequestBody userCheckDto: UserCheckDto): List<FileDto>{
                if(!securityService.hasPermission(userCheckDto))
                        throw Exception("У вас нет доступа к запршиваему ресурсу!")
                return userService.getFilesForUser(userCheckDto.username)
        }

        @PostMapping("/file/read")
        fun readFile(@RequestBody dto: CreateFileDto): String{
                if(!securityService.hasPermission(dto.userCheck))
                        throw Exception("У вас нет доступа к запршиваему ресурсу!")
                return userService.readFile(dto.userCheck.username,dto.fileName)
        }

        @PostMapping("/file/write")
        fun writeFile(@RequestBody dto: WriteFileDto){
                if(!securityService.hasPermission(dto.userCheck))
                        throw Exception("У вас нет доступа к запршиваему ресурсу!")
                return userService.writeFile(dto.userCheck.username,dto.fileName,dto.text)
        }


        @PostMapping("/file/takegrant")
        fun takeGrant(@RequestBody dto: TakeGrantDto){
                if(!securityService.hasPermission(dto.userCheck))
                        throw Exception("У вас нет доступа к запршиваему ресурсу!")

                return userService.takeGrant(dto.userCheck.username,dto.fileName,dto.grantName,dto.usernameNew)
        }
}