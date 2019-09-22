package com.kkaminsky.simplelogin.repository

import com.kkaminsky.simplelogin.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository: JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?

    @Query("select * from public.user as u join public.role as r on u.role_id = r.id where r.role = :roleType",nativeQuery = true)
    fun findByRoleType(@Param("roleType") roleType: String): List<UserEntity>
}