package com.kkaminsky.simplelogin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FileDto(

        @JsonProperty("id")
        val id: Long,

        @JsonProperty("fileName")
        val fileName: String,

        @JsonProperty("grants")
        val grants: List<String>
)