package com.dicoding.submission1storyapp.ui.addingstory


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submission1storyapp.data.local.UserStoryPreference
import com.dicoding.submission1storyapp.data.remote.api.ApiConfig
import com.dicoding.submission1storyapp.data.remote.response.GeneralErrorResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewStoryViewModel(private val preference: UserStoryPreference) : ViewModel() {

    private val _isLoading  = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError

    private val client = ApiConfig.getApiService()

    fun postNewStory(userToken: String, file: MultipartBody.Part, description: RequestBody) {
        _isLoading.value = true
        client.uploadNewStory(token = "Bearer $userToken", file, description).enqueue(object : Callback<GeneralErrorResponse>{
            override fun onResponse(
                call: Call<GeneralErrorResponse>,
                response: Response<GeneralErrorResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _messageError.value = response.body()?.message
                } else {
                    _messageError.value = response.message().toString()
                }
            }

            override fun onFailure(call: Call<GeneralErrorResponse>, t: Throwable) {
                _isLoading.value = false
                _messageError.value = t.message
            }

        })
    }

    fun dataState(): LiveData<String> {
        return preference.getTokenKey().asLiveData()
    }
}