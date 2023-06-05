package com.dicoding.submission1storyapp.ui.main

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.submission1storyapp.data.remote.response.ListUserStoriesItem
import com.dicoding.submission1storyapp.data.remote.response.UserStoryRepository
import com.dicoding.submission1storyapp.utils.Injection

class MainActivityViewModel(userStoryRepository: UserStoryRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val userStoires: LiveData<PagingData<ListUserStoriesItem>> = userStoryRepository.getAllUserStories().cachedIn(viewModelScope)

    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(Injection.provideStoryRepository(context)) as T
            }
            throw IllegalArgumentException("UNKNOWN ViewModel class")
        }
    }
}