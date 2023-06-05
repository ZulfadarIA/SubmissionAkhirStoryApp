package com.dicoding.submission1storyapp.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission1storyapp.R
import com.dicoding.submission1storyapp.data.UserPreference
import com.dicoding.submission1storyapp.databinding.ActivityMainBinding
import com.dicoding.submission1storyapp.ui.adapter.LoadingStateAdapter
import com.dicoding.submission1storyapp.ui.adapter.StoriesAdapter
import com.dicoding.submission1storyapp.ui.addingstory.AddNewStoryActivity
import com.dicoding.submission1storyapp.ui.login.LoginUserActivity
import com.dicoding.submission1storyapp.ui.maps.MapsActivity
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preference: UserPreference
    private val mainViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference(this)
        val userToken: String = intent.getStringExtra("TOKEN").toString()
        setRecycleViewMain(userToken)

        mainViewModel.isLoading.observe(this) { loading ->
            showingLoading(loading)
        }
        navigateToAddNewStoryActivity()
    }

    private fun navigateToAddNewStoryActivity() {
        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, AddNewStoryActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                lifecycleScope.launch {
                    preference.clearUserToken()
                }
                val intent = Intent(this, LoginUserActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }
            R.id.options_maps -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                true
            }
        }
    }

    private fun showingLoading(isLoading: Boolean) {
        binding.loadingItemBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setRecycleViewMain(userToken: String) {
        val adapterMain = StoriesAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvStoryList.layoutManager = layoutManager
        binding.rvStoryList.adapter = adapterMain
        binding.rvStoryList.adapter = adapterMain.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapterMain.retry()
            }
        )
        mainViewModel.userStoires.observe(this){
            adapterMain.submitData(lifecycle, it)
        }
    }
}