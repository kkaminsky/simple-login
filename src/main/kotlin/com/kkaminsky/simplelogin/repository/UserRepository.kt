package com.kkaminsky.simplelogin.repository

import com.kkaminsky.simplelogin.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository: JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?
}