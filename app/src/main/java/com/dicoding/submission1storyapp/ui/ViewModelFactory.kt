package com.dicoding.submission1storyapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission1storyapp.data.local.UserStoryPreference
import com.dicoding.submission1storyapp.ui.addingstory.AddNewStoryViewModel
import com.dicoding.submission1storyapp.ui.detailstory.DetailStoryActivity
import com.dicoding.submission1storyapp.ui.detailstory.DetailStoryViewModel
import com.dicoding.submission1storyapp.ui.login.LoginUserViewModel
import com.dicoding.submission1storyapp.ui.main.MainActivityViewModel
import com.dicoding.submission1storyapp.ui.maps.MapsViewModel

class ViewModelFactory(private val preference: UserStoryPreference) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(DetailStoryViewModel::class.java) -> {
                DetailStoryViewModel(preference) as T
            }
            modelClass.isAssignableFrom(LoginUserViewModel::class.java) -> {
                LoginUserViewModel(preference) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(preference) as T
            }
            modelClass.isAssignableFrom(AddNewStoryViewModel::class.java) -> {
                AddNewStoryViewModel(preference) as T
            }
            else -> throw IllegalArgumentException("Uknown ViewModel class: " + modelClass.name)
        }
    }
}