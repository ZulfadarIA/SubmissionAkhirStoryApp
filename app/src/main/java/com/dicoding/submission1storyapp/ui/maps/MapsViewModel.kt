package com.dicoding.submission1storyapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submission1storyapp.data.local.UserStoryPreference
import com.dicoding.submission1storyapp.data.remote.api.ApiConfig
import com.dicoding.submission1storyapp.data.remote.response.ListUserStoriesItem
import com.dicoding.submission1storyapp.data.remote.response.UserStoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val preference: UserStoryPreference) : ViewModel() {

    private val _stories = MutableLiveData<List<ListUserStoriesItem>>()
    val stories: LiveData<List<ListUserStoriesItem>> = _stories

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError

    private val client = ApiConfig.getApiService()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllStoriesOnMapLocation(userToken: String) {
        _isLoading.value = true
        client.getAllUserStoriesWithLocation("Bearer $userToken", 1).enqueue(object : Callback<UserStoriesResponse> {
            override fun onResponse(
                call: Call<UserStoriesResponse>,
                response: Response<UserStoriesResponse>
            ) {
                _isLoading.value = false
                _messageError.value = response.body()?.message
                if (response.isSuccessful){
                    _stories.value = response.body()?.listStory
                }
            }

            override fun onFailure(call: Call<UserStoriesResponse>, t: Throwable) {
                _isLoading.value =false
                _messageError.value = t.message
            }
        })
    }

    fun dataState(): LiveData<String> {
        return preference.getTokenKey().asLiveData()
    }
}