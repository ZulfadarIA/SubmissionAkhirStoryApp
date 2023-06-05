package com.dicoding.submission1storyapp.ui.detailstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission1storyapp.data.local.UserStoryPreference
import com.dicoding.submission1storyapp.data.remote.response.UserStory

class DetailStoryViewModel(private val preference: UserStoryPreference) : ViewModel() {

    private val _stories = MutableLiveData<UserStory>()
    val stories: LiveData<UserStory> = _stories
}