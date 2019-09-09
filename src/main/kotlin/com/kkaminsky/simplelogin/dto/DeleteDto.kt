package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DeleteDto(

        @JsonProperty("userCheck")
        val userCheck: UserCheckDto,

        @JsonProperty("username")
        val username: String

)