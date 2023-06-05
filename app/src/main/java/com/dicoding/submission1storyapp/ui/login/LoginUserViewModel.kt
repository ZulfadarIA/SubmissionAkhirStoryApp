package com.dicoding.submission1storyapp.ui.login

import androidx.lifecycle.*
import com.dicoding.submission1storyapp.data.local.UserStoryPreference
import com.dicoding.submission1storyapp.data.remote.api.ApiConfig
import com.dicoding.submission1storyapp.data.remote.response.LoginUserResponse
import com.dicoding.submission1storyapp.data.remote.response.LoginUserResult
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginUserViewModel(private val preference: UserStoryPreference) : ViewModel() {
    private val _dataLogin = MutableLiveData<LoginUserResult>()
    val dataLogin: LiveData<LoginUserResult> = _dataLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val client = ApiConfig.getApiService()

    private val _messageResponse = MutableLiveData<String>()
    val messageResponse: LiveData<String> = _messageResponse

    fun dataState(): LiveData<String> {
        return  preference.getTokenKey().asLiveData()
    }

    fun userLogin(emailUser: String, password: String) {
        _isLoading.value = true
        client.userLogin(emailUser, password).enqueue(object : Callback<LoginUserResponse> {
            override fun onResponse(
                call: Call<LoginUserResponse>,
                response: Response<LoginUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _dataLogin.value = response.body()?.loginResult
                    _messageResponse.value = response.body()?.message
                    viewModelScope.launch {
                        preference.saveTokenKey(response.body()?.loginResult?.token.toString())
                    }
                } else {
                    _messageResponse.value = response.message()
                }
            }

            override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                _isLoading.value = false
                _messageResponse.value = t.message
            }

        })
    }
}