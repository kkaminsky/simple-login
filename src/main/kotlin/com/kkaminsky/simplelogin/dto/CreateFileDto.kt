package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateFileDto(

        @JsonProperty("userCheck")
        val userCheck: UserCheckDto,

        @JsonProperty("fileName")
        val fileName: String
)