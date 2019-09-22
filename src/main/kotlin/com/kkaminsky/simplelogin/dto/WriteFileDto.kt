package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class WriteFileDto(

        @JsonProperty("fileName")
        val fileName: String,

        @JsonProperty("text")
        val text: String,

        @JsonProperty("userCheck")
        val userCheck: UserCheckDto
)