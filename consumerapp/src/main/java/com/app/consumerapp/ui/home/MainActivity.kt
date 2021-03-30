package com.app.consumerapp.ui.home

import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.consumerapp.customview.DividerItemDecorationWithoutLast
import com.app.consumerapp.data.User
import com.app.consumerapp.databinding.ActivityMainBinding
import com.app.consumerapp.helper.MappingHelper
import com.app.consumerapp.ui.adapter.UserListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val contentUri: Uri = Uri.Builder().scheme("content")
        .authority("com.app.githubmobile")
        .appendPath("favorite")
        .build()

    private lateinit var binding: ActivityMainBinding


    private lateinit var userAdapter: UserListAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserListAdapter()

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = userAdapter
            addItemDecoration(DividerItemDecorationWithoutLast(this@MainActivity))
        }

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(contentUri, true, myObserver)

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
                ?.also { userAdapter.setData(it) }
        }
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFav = async(Dispatchers.IO) {
                val cursor = contentResolver.query(contentUri, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorites = deferredFav.await()

            if (favorites.size > 0) {
                binding.rvFavorite.visibility = View.VISIBLE
                binding.favoriteEmpty.root.visibility = View.GONE
                userAdapter.setData(favorites)
            } else {
                binding.rvFavorite.visibility = View.GONE
                binding.favoriteEmpty.root.visibility = View.VISIBLE
                userAdapter.setData(emptyList())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, userAdapter.getList)
    }
}