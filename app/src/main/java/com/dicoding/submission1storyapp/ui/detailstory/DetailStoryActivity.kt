package com.dicoding.submission1storyapp.ui.detailstory

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.submission1storyapp.data.local.UserStoryPreference
import com.dicoding.submission1storyapp.data.remote.response.ListUserStoriesItem
import com.dicoding.submission1storyapp.databinding.ActivityDetailStoryBinding
import com.dicoding.submission1storyapp.ui.ViewModelFactory
import com.dicoding.submission1storyapp.ui.main.dataStore
import com.google.android.material.snackbar.Snackbar

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var detailStoryViewModel: DetailStoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailStoryViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserStoryPreference.getInstance(dataStore))
        )[DetailStoryViewModel::class.java]

        val storyDetail = intent.getParcelableExtra<ListUserStoriesItem>("STORY")
        binding.apply {
            Glide.with(this@DetailStoryActivity).load(storyDetail?.photoUrl).into(detailPhotoIv)
            detailFotoNamaTv.text = storyDetail?.name
            detailDescriptionTv.text = storyDetail?.description
        }

        setDetailAnimation()
    }

    private fun setDetailAnimation() {
        ObjectAnimator.ofFloat(binding.cardV, View.TRANSLATION_Y, 0f, 1f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.cardV, View.ALPHA, 0f, 1f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.cardV, View.SCALE_X, 0f, 1f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.cardV, View.SCALE_Y, 0f, 1f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.usernameTv, View.SCALE_X, 0f, 1f).apply {
            duration = 1500
        }.start()
        ObjectAnimator.ofFloat(binding.descriptionTv, View.SCALE_X, 0f, 1f).apply {
            duration = 1500
        }.start()
    }
}