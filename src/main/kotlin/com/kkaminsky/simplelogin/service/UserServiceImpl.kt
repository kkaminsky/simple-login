package com.kkaminsky.simplelogin.service

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
import java.lang.RuntimeException
import java.security.Signature

@Service
class UserServiceImpl @Autowired constructor(
        val userRepository: UserRepository,
        val roleRepository: RoleRepository,
        val encoder: PasswordEncoder,
        val signatureService: SignatureService
): UserService {

    override fun getAllUsers(): List<UsernameDto> {
        return userRepository.findAll().map { userEntity -> UsernameDto(userEntity.username!!) }
    }

    override fun delete(username: String) {

        val user = userRepository.findByUsername(username)?:throw Exception("Пользователь $username не существует!")
        userRepository.delete(user)

    }

    override fun edit(oldUsername: String, newUsername: String) {
        val user = userRepository.findByUsername(oldUsername)?:throw Exception("Пользователь $oldUsername не существует!")
        user.username = newUsername
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


    override fun login(username: String, password: String): UserDto {

        val user = userRepository.findByUsername(username) ?: throw Exception("Пользователя $username не существует!")

        if (encoder.matches(password,user.password)){
            return UserDto(
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