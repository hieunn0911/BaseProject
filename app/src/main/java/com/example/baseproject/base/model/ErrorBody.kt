package com.example.baseproject.base.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorBody(
    @Json(name = "message")
    val message: String = "",
    @Json(name = "code")
    val code: String = ""
)