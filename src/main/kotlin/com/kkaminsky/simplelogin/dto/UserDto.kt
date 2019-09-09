package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserDto(

        @JsonProperty("id")
        val id: Long,

        @JsonProperty("username")
        val username: String,

        @JsonProperty("role")
        val role: String
)