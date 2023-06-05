package com.dicoding.submission1storyapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    @field:SerializedName("loginResult")
    val loginResult: LoginUserResult,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginUserResult(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)
