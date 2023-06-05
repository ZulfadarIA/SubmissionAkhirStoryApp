package com.dicoding.submission1storyapp.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission1storyapp.data.remote.api.ApiConfig
import com.dicoding.submission1storyapp.data.remote.response.GeneralErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegitersUserViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val client = ApiConfig.getApiService()

    private  val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun userRegister(username: String, emailUser: String, password: String) {
        _isLoading.value = true
        client.userRegister(username, emailUser, password).enqueue(object : Callback<GeneralErrorResponse> {
            override fun onResponse(
                call: Call<GeneralErrorResponse>,
                response: Response<GeneralErrorResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = response.body()?.message
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<GeneralErrorResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }
        })
    }
}