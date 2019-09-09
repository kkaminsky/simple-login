package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EditDto(

        @JsonProperty("userCheck")
        val userCheck: UserCheckDto,

        @JsonProperty("oldUsername")
        val oldUsername: String,

        @JsonProperty("newUsername")
        val newUsername: String,

        @JsonProperty("newRole")
        val newRole: String
)