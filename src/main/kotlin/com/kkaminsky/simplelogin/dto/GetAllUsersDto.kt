package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GetAllUsersDto(

        @JsonProperty("userCheck")
        val userCheck: UserCheckDto

)