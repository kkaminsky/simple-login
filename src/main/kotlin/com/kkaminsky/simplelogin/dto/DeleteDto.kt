package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DeleteDto(

        @JsonProperty("user")
        val user: UserDto,

        @JsonProperty("username")
        val username: String

)