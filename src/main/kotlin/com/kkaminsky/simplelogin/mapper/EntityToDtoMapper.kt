package com.kkaminsky.simplelogin.mapper

import com.kkaminsky.simplelogin.dto.UserCheckDto
import com.kkaminsky.simplelogin.model.UserEntity
import org.mapstruct.Mapper
import org.mapstruct.ObjectFactory

@Mapper(componentModel = "spring",uses= [ObjectFactory::class])
interface EntityToDtoMapper {

    fun map(userEntity: UserEntity): UserCheckDto
}