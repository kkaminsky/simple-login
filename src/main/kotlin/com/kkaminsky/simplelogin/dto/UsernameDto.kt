package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UsernameDto(

        @JsonProperty("username")
        val username: String
)