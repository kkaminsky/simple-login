package com.kkaminsky.simplelogin.service

import com.kkaminsky.simplelogin.dto.UserCheckDto
import com.kkaminsky.simplelogin.dto.UserDto
import com.kkaminsky.simplelogin.dto.UsernameDto
import com.kkaminsky.simplelogin.model.RoleEntity
import com.kkaminsky.simplelogin.model.UserEntity
import com.kkaminsky.simplelogin.repository.RoleRepository
import com.kkaminsky.simplelogin.repository.UserRepository
import com.kkaminsky.simplelogin.security.SignatureService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
        val userRepository: UserRepository,
        val roleRepository: RoleRepository,
        val encoder: PasswordEncoder,
        val signatureService: SignatureService
): UserService {
    override fun registerAdmin(username: String, password: String) {
        val user = userRepository.findByUsername(username)

        if (user!=null) throw Exception("Пользователь $username уже существует!")

        val newUser =  UserEntity()
        newUser.username = username
        newUser.password = encoder.encode(password)

        var role = roleRepository.findByRole("ROLE_ADMIN")

        if (role==null) {
            role = RoleEntity()
            role.role = "ROLE_ADMIN"
            roleRepository.save(role)
        }

        newUser.role = role

        userRepository.save(newUser)
    }

    override fun getAllUsers(): List<UserDto> {
        return userRepository.findAll().map { userEntity -> UserDto(userEntity.id, userEntity.username!!, userEntity.role!!.role!!) }
    }

    override fun delete(username: String) {

        val user = userRepository.findByUsername(username)?:throw Exception("Пользователь $username не существует!")
        userRepository.delete(user)

    }

    override fun edit(oldUsername: String, newUsername: String, newRole: String) {

        val role = roleRepository.findByRole(newRole)?:throw  Exception("Роль не найден!")
        val user = userRepository.findByUsername(oldUsername)?:throw Exception("Пользователь $oldUsername не существует!")

        user.username = newUsername
        user.role = role

        userRepository.save(user)
    }

    override fun register(username: String, password: String) {

        val user = userRepository.findByUsername(username)

        if (user!=null) throw Exception("Пользователь $username уже существует!")

        val newUser =  UserEntity()
        newUser.username = username
        newUser.password = encoder.encode(password)

        var role = roleRepository.findByRole("ROLE_USER")

        if (role==null) {
            role = RoleEntity()
            role.role = "ROLE_USER"
            roleRepository.save(role)
        }

        newUser.role = role

        userRepository.save(newUser)

    }


    override fun login(username: String, password: String): UserCheckDto {

        val user = userRepository.findByUsername(username) ?: throw Exception("Пользователя $username не существует!")

        if (encoder.matches(password,user.password)){
            return UserCheckDto(
                    username = user.username!!,
                    userRole = user.role!!.role!!,
                    signature = signatureService.getSign(user.username!! + user.role!!.role!!)
            )
        }
        else {
            throw Exception("Неверный пароль!")
        }

    }

}