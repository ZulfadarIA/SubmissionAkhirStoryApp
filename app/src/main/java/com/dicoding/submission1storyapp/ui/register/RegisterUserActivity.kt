package com.dicoding.submission1storyapp.ui.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission1storyapp.R
import com.dicoding.submission1storyapp.databinding.ActivityRegisterUserBinding
import com.dicoding.submission1storyapp.ui.login.LoginUserActivity
import com.google.android.material.snackbar.Snackbar

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUserBinding
    private lateinit var userRegisterViewModel: RegitersUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRegisterViewModel = ViewModelProvider(this)[RegitersUserViewModel::class.java]

        binding.registerBtn.setOnClickListener {
            val username = binding.usernameEdt.text.toString().trim()
            val emailUser = binding.emailEdt.text.toString().trim()
            val password = binding.passwordEdt.text.toString().trim()

            if (username.isNotEmpty() && emailUser.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 8) {
                    userRegisterViewModel.userRegister(
                        username, emailUser, password
                    )
                } else {
                    formMessageToast("Isi form terlebih dahulu!")
                }
            }

            userRegisterViewModel.message.observe(this) { message ->
                formMessageToast(message)
                if (message == "User created") {
                    startActivity(Intent(this, LoginUserActivity::class.java))
                    finish()
                }
            }
            
            userRegisterViewModel.isLoading.observe(this) { isLoading ->
                showingLoading(isLoading)
            }
        }

        supportActionBar?.hide()
    }

    private fun showingLoading(loading: Boolean) {
        binding.loadingBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun formMessageToast(messageForm: String) {
        if (messageForm == "Bad request") {
            val messageChange = "Email ini sudah pernah dibuat!"
            Toast.makeText(this, messageChange, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, messageForm, Toast.LENGTH_SHORT).show()
        }
    }
}