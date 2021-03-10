package com.app.githubmobile.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.coremodule.data.Resource
import com.app.githubmobile.adapter.UserListAdapter
import com.app.githubmobile.databinding.ActivityDashboardBinding
import com.app.githubmobile.detail.DetailActivity
import com.app.githubmobile.favorite.FavoriteActivity
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        val userAdapter = UserListAdapter()

        viewModel.getAllUser().observe(this) { users ->
            when (users) {
                is Resource.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    val data = users.data
                    if (data != null) {
                        userAdapter.setData(data)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        userAdapter.onClickItem = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.dataKey, it)
            startActivity(intent)
        }

        binding.rvUser.let {
            it.layoutManager = LinearLayoutManager(this@DashboardActivity)
            it.hasFixedSize()
            it.adapter = userAdapter
            it.addItemDecoration(DividerItemDecoration(it.context, DividerItemDecoration.VERTICAL))
        }

        binding.btnFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }
}