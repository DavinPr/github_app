package com.app.githubmobile.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.coremodule.data.Resource
import com.app.githubmobile.R
import com.app.githubmobile.adapter.UserListAdapter
import com.app.githubmobile.databinding.ActivityFavoriteBinding
import com.app.githubmobile.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.apply {
            navigationIcon = ContextCompat.getDrawable(this@FavoriteActivity, R.drawable.ic_back)
            setNavigationOnClickListener {
                finish()
            }
        }

        val userAdapter = UserListAdapter()

        viewModel.getAllFavorite().observe(this) { favorite ->
            when (favorite) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val data = favorite.data
                    if (data.isNullOrEmpty()) {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.data_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.rvFavorite.visibility = View.GONE
                        binding.favoriteEmpty.root.visibility = View.VISIBLE
                    } else {
                        binding.rvFavorite.visibility = View.VISIBLE
                        binding.favoriteEmpty.root.visibility = View.GONE
                        userAdapter.setData(data)
                    }
                }
                is Resource.Error -> {
                }
            }
        }

        userAdapter.onClickItem = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.dataKey, it)
            startActivity(intent)
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            hasFixedSize()
            adapter = userAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}