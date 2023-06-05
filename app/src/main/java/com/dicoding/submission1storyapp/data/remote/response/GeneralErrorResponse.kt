package com.dicoding.submission1storyapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GeneralErrorResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
