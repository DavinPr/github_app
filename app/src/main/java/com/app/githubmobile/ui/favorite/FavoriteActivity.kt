package com.app.githubmobile.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.model.User
import com.app.githubmobile.R
import com.app.githubmobile.adapter.UserListAdapter
import com.app.githubmobile.customview.DividerItemDecorationWithoutLast
import com.app.githubmobile.databinding.ActivityFavoriteBinding
import com.app.githubmobile.ui.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var userAdapter: UserListAdapter

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

        userAdapter = UserListAdapter()

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
            addItemDecoration(DividerItemDecorationWithoutLast(context))
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private val itemTouchHelper: ItemTouchHelper =
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPosition = viewHolder.adapterPosition
                val user: User = userAdapter.getSwipedData(swipedPosition)
                viewModel.deleteFavoriteByUsername(user.username)
                val snackbar = Snackbar.make(
                    binding.root,
                    resources.getString(R.string.delete_data),
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }
        })
}