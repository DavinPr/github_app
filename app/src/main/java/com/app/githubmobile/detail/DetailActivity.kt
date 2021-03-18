package com.app.githubmobile.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.githubmobile.R
import com.app.githubmobile.databinding.ActivityDetailBinding
import com.app.githubmobile.detail.datadisplay.DetailDataFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val dataKey = "data_key"
        const val FRAGMENT_RESULT = "fragment_result"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModel<DetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {

            Log.d("Load", "null")

            val username = intent.getStringExtra(dataKey)

            val dataFragment = DetailDataFragment()
            val bundle = Bundle()
            bundle.putString(DetailDataFragment.dataKey, username)
            dataFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .add(
                    R.id.detail_fragment_container,
                    dataFragment
                )
                .commit()
        } else {
            Log.d("Load", "twice")
            supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_RESULT)
        }
    }
}