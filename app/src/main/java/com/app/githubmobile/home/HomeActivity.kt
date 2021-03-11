package com.app.githubmobile.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.githubmobile.R
import com.app.githubmobile.databinding.ActivityHomeBinding
import com.app.githubmobile.home.dashboard.DashboardFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.home_container, DashboardFragment())
            .commit()
    }
}