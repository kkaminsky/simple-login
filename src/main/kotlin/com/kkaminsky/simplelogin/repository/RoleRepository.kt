package com.kkaminsky.simplelogin.repository

import com.kkaminsky.simplelogin.model.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<RoleEntity, Long>{
    fun findByRole(role: String): RoleEntity?
}