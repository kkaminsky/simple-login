package com.kkaminsky.simplelogin.repository

import com.kkaminsky.simplelogin.model.GrantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GrantRepository: JpaRepository<GrantEntity, Long> {
}