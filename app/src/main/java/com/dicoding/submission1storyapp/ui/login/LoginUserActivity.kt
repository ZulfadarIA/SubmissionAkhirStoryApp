package com.dicoding.submission1storyapp.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.submission1storyapp.data.local.UserStoryPreference
import com.dicoding.submission1storyapp.databinding.ActivityLoginUserBinding
import com.dicoding.submission1storyapp.ui.ViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission1storyapp.data.UserPreference
import com.dicoding.submission1storyapp.data.remote.response.LoginUserResult
import com.dicoding.submission1storyapp.ui.main.MainActivity
import com.dicoding.submission1storyapp.ui.register.RegisterUserActivity

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")

class LoginUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUserBinding
    private lateinit var userLoginViewModel: LoginUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userLoginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserStoryPreference.getInstance(dataStore))
        )[LoginUserViewModel::class.java]
        
        binding.loginBtn.setOnClickListener { 
            val emailUser = binding.emailEdt.text.toString().trim()
            val password = binding.passwordEdt.text.toString().trim()
            
            if (emailUser.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 8) {
                    userLoginViewModel.userLogin(
                        emailUser, password
                    )
                    userLoginViewModel.dataLogin.observe(this) { data -> 
                        savedTokenKey(data)
                    }
                } else {
                    formMessageToast("Isi form terlebih dahulu!")
                }
            }
        }
        binding.toRegisterPage.setOnClickListener { 
            startActivity(Intent(this, RegisterUserActivity::class.java))
        }

        userLoginViewModel.messageResponse.observe(this) {message ->
            formMessageToast(message)
            if (message == "success") {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

        userLoginViewModel.isLoading.observe(this) { isLoading ->
            showingLoading(isLoading)
        }

        supportActionBar?.hide()
    }

    private fun showingLoading(loading: Boolean) {
        binding.loadingBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun formMessageToast(message: String) {
        val messageChange: String
        if (message == "Bad request" || message == "Unauthorized") {
            messageChange = "Email atau password yang Anda masukkan invalid!"
            Toast.makeText(this, messageChange, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun savedTokenKey(data: LoginUserResult?) {
        val preference = com.dicoding.submission1storyapp.data.UserPreference(applicationContext)
        preference.setUserToken(data?.token.toString())
    }


}