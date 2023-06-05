package com.dicoding.submission1storyapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission1storyapp.data.local.UserStoryPreference
import com.dicoding.submission1storyapp.databinding.ActivitySplashScreenBinding
import com.dicoding.submission1storyapp.ui.login.LoginUserActivity
import com.dicoding.submission1storyapp.ui.login.LoginUserViewModel
import com.dicoding.submission1storyapp.ui.main.MainActivity
import kotlinx.coroutines.*
import java.util.prefs.Preferences

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var userLoginViewModel: LoginUserViewModel
    private lateinit var userToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userLoginViewModel = ViewModelProvider(this, ViewModelFactory(UserStoryPreference.getInstance(dataStore)))[LoginUserViewModel::class.java]

        userLoginViewModel.dataState().observe(this) {
            userToken = it
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (userToken.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("TOKEN", userToken)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginUserActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
        supportActionBar?.hide()
    }
}