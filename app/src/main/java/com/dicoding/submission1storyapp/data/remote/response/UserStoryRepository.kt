package com.dicoding.submission1storyapp.data.remote.response

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.submission1storyapp.data.StoryPagingSource
import com.dicoding.submission1storyapp.data.UserPreference
import com.dicoding.submission1storyapp.data.remote.api.ApiService

class UserStoryRepository(private val apiService: ApiService, private val preference: UserPreference) {
    val userToken = preference.getUserToken()

    fun getAllUserStories(): LiveData<PagingData<ListUserStoriesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, userToken.toString())
            }
        ).liveData
    }
}