package com.dicoding.submission1storyapp.utils

import android.content.Context
import com.dicoding.submission1storyapp.data.UserPreference
import com.dicoding.submission1storyapp.data.remote.api.ApiConfig
import com.dicoding.submission1storyapp.data.remote.response.UserStoryRepository

object Injection {
    fun provideStoryRepository(context: Context) : UserStoryRepository {
        val apiService = ApiConfig.getApiService()
        val preference = UserPreference(context)
        return UserStoryRepository(apiService, preference)
    }
}