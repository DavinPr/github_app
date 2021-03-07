package com.app.githubmobile.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.githubmobile.R
import com.app.githubmobile.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object{
        const val dataKey = "data_key"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}