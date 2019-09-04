package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterDto(

        @JsonProperty("username")
        val username: String,

        @JsonProperty("password")
        val password: String

)