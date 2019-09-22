package com.kkaminsky.simplelogin.service

import com.kkaminsky.simplelogin.dto.FileDto
import com.kkaminsky.simplelogin.dto.UserCheckDto
import com.kkaminsky.simplelogin.dto.UserDto
import com.kkaminsky.simplelogin.dto.UsernameDto
import com.kkaminsky.simplelogin.model.FileEntity
import com.kkaminsky.simplelogin.model.GrantEntity
import com.kkaminsky.simplelogin.model.RoleEntity
import com.kkaminsky.simplelogin.model.UserEntity
import com.kkaminsky.simplelogin.repository.FileRepository
import com.kkaminsky.simplelogin.repository.GrantRepository
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
        val fileRepository: FileRepository,
        val grantRepository: GrantRepository,
        val encoder: PasswordEncoder,
        val signatureService: SignatureService
): UserService {
    override fun getFilesForUser(username: String): List<FileDto> {
        return fileRepository.findAll()
                .filter { file -> file.grants.map { g -> g.user!!.username }.contains(username) }
                .map {
            FileDto(
                    id = it.id,
                    fileName = it.fileName!!,
                    grants = it.grants.filter { g->g.user!!.username == username }.map { g -> g.type!!}
            )
        }

    }

    override fun takeGrant(username: String, fileName: String, grantName: String, usernameNew: String) {
        val user1 = userRepository.findByUsername(username)?:throw Exception("Пользователь $username не существует!")
        val file = fileRepository.findByFileName(fileName)?:throw Exception("Файл $fileName не найден!")
        val user2 = userRepository.findByUsername(usernameNew)?:throw Exception("Пользователь $usernameNew не существует!")

        if(grantName == "READ" || grantName == "WRITE"){
            val grant = user1.grants.find { grant -> grant.file == file && grant.type == "TG" }?:throw Exception("Пользователь не может передать данное право!")



            /*println(user1.grants.count())
            grantRepository.delete(grant)
            println(user1.grants.count())*/

            val newGrant = GrantEntity()
            newGrant.type = grantName
            newGrant.file = file
            newGrant.user = user2
            grantRepository.save(newGrant)
        }

        if(grantName == "TG"){
           user1.grants.find { grant -> grant.file == file && grant.type == "OWN" }?:throw Exception("Пользователь не может передать данное право!")

            /*println(user1.grants.count())
            grantRepository.delete(grant)
            println(user1.grants.count())*/

            val check = user2.grants.find { grant -> grant.file == file && grant.type == "TG" }

            if(check!=null){
                throw Exception("Пользователь уже имеет данное право!")
            }

            val newGrant = GrantEntity()
            newGrant.type = grantName
            newGrant.file = file
            newGrant.user = user2
            grantRepository.save(newGrant)
        }

        if(grantName == "OWN"){
            val grant = user1.grants.find { grant -> grant.file == file && grant.type == "OWN" }?:throw Exception("Пользователь не может передать данное право!")



            println(user1.grants.count())
            grantRepository.delete(grant)
            println(user1.grants.count())

            val newGrant = GrantEntity()
            newGrant.type = grantName
            newGrant.file = file
            newGrant.user = user2
            grantRepository.save(newGrant)
        }


    }

    override fun writeFile(username: String, fileName: String, text: String) {
        val user = userRepository.findByUsername(username)?:throw Exception("Пользователь $username не существует!")
        val file = fileRepository.findByFileName(fileName)?:throw Exception("Файл $fileName не найден!")

        user.grants.find { grant -> grant.file == file && grant.type == "WRITE" }?:throw Exception("Пользователь не может записать в данный файл!")

        file.text += text

        fileRepository.save(file)
    }

    override fun readFile(username: String, fileName: String) :String {
        val user = userRepository.findByUsername(username)?:throw Exception("Пользователь $username не существует!")
        val file = fileRepository.findByFileName(fileName)?:throw Exception("Файл $fileName не найден!")

        user.grants.find { grant -> grant.file == file && grant.type == "READ" }?:throw Exception("Пользователь не может читать данный файл!")

        return file.text.toString()
    }


    override fun deleteFile(username: String, fileName: String) {
        val user = userRepository.findByUsername(username)?:throw Exception("Пользователь $username не существует!")
        val file = fileRepository.findByFileName(fileName)?:throw Exception("Файл $fileName не найден!")

        user.grants.find { grant -> grant.file == file && grant.type == "OWN" }?:throw Exception("Пользователь не может удалить данный файл!")

        fileRepository.delete(file)


    }

    override fun createUserFile(username: String, fileName: String) {

        val user = userRepository.findByUsername(username)?:throw Exception("Пользователь $username не существует!")
        val file = fileRepository.findByFileName(fileName)

        if (file!=null) throw Exception("Файл с именем $fileName уже существует!")

        val newFile = FileEntity()

        newFile.fileName = fileName

        fileRepository.save(newFile)

        val grantRead = GrantEntity()
        grantRead.file = newFile
        grantRead.user = user
        grantRead.type = "READ"

        newFile.grants.add(grantRead)
        user.grants.add(grantRead)
        grantRepository.save(grantRead)

        val grantWrite = GrantEntity()
        grantWrite.file = newFile
        grantWrite.user = user
        grantWrite.type = "WRITE"

        newFile.grants.add(grantWrite)
        user.grants.add(grantWrite)
        grantRepository.save(grantWrite)

        val grantTakeGrant = GrantEntity()
        grantTakeGrant.file = newFile
        grantTakeGrant.user = user
        grantTakeGrant.type = "TG"

        newFile.grants.add(grantTakeGrant)
        user.grants.add(grantTakeGrant)
        grantRepository.save(grantTakeGrant)

        val grantOwn = GrantEntity()
        grantOwn.file = newFile
        grantOwn.user = user
        grantOwn.type = "OWN"

        newFile.grants.add(grantOwn)
        user.grants.add(grantOwn)
        grantRepository.save(grantOwn)

        val admins= userRepository.findByRoleType("ROLE_ADMIN")

        for(user in admins){

            val grantRead = GrantEntity()
            grantRead.file = newFile
            grantRead.user = user
            grantRead.type = "READ"

            newFile.grants.add(grantRead)
            user.grants.add(grantRead)
            grantRepository.save(grantRead)

            val grantWrite = GrantEntity()
            grantWrite.file = newFile
            grantWrite.user = user
            grantWrite.type = "WRITE"

            newFile.grants.add(grantWrite)
            user.grants.add(grantWrite)
            grantRepository.save(grantWrite)

            val grantTakeGrant = GrantEntity()
            grantTakeGrant.file = newFile
            grantTakeGrant.user = user
            grantTakeGrant.type = "TG"

            newFile.grants.add(grantTakeGrant)
            user.grants.add(grantTakeGrant)
            grantRepository.save(grantTakeGrant)

            val grantOwn = GrantEntity()
            grantOwn.file = newFile
            grantOwn.user = user
            grantOwn.type = "OWN"


            newFile.grants.add(grantOwn)
            user.grants.add(grantOwn)
            grantRepository.save(grantOwn)

            userRepository.save(user)


        }

        userRepository.save(user)
        fileRepository.save(newFile)

    }

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