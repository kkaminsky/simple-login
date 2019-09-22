package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TakeGrantDto(

        @JsonProperty("userCheck")
        val userCheck: UserCheckDto,

        @JsonProperty("fileName")
        val fileName: String,

        @JsonProperty("usernameNew")
        val usernameNew: String,

        @JsonProperty("grantName")
        val grantName: String
)