package com.kkaminsky.simplelogin.repository

import com.kkaminsky.simplelogin.model.FileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository: JpaRepository<FileEntity, Long> {

    fun findByFileName(fileName: String): FileEntity?
}