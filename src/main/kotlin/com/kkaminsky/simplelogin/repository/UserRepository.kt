package com.kkaminsky.simplelogin.repository

import com.kkaminsky.simplelogin.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}