package com.dicoding.submission1storyapp.data.remote.api


import com.dicoding.submission1storyapp.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun userRegister(
        @Field("name") Name: String,
        @Field("email") Email: String,
        @Field("password") Password: String
    ): Call<GeneralErrorResponse>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") Email: String,
        @Field("password") Password: String
    ): Call<LoginUserResponse>

    @GET("stories")
    suspend fun getAllUserStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): UserStoriesResponse

    @GET("stories")
    fun getAllUserStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 0
    ): Call<UserStoriesResponse>

    @GET("stories/{id}")
    fun detailUserStory(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): Call<DetailUserStoryResponse>

    @Multipart
    @POST("stories")
    fun uploadNewStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<GeneralErrorResponse>
}