package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EditDto(

        @JsonProperty("user")
        val user: UserDto,

        @JsonProperty("oldUsername")
        val oldUsername: String,

        @JsonProperty("newUsername")
        val newUsername: String
)