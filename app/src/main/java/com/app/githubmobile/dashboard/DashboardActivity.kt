package com.app.githubmobile.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.coremodule.data.Resource
import com.app.githubmobile.databinding.ActivityDashboardBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.getAllUser().observe(this) { users ->
            when (users) {
                is Resource.Loading -> {
                    val text = "Loading"
                    binding.text.text = text
                }
                is Resource.Success -> {
                }
                is Resource.Error -> {
                    val text = "Error"
                    binding.text.text = text
                }
            }
        }
        var state = true
        binding.favorite.setState(state)
        binding.favorite.setOnClickListener {
            state = !state
            binding.favorite.setState(!state)
        }

    }
}