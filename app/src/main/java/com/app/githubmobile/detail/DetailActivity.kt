package com.app.githubmobile.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.coremodule.data.Resource
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

            val username = intent.getStringExtra(dataKey)

            if (username != null) {
                viewModel.getDetailData(username).observe(this) { detail ->
                    when (detail) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            val data = detail.data
                            val dataFragment = DetailDataFragment()
                            val bundle = Bundle()
                            bundle.putParcelable(DetailDataFragment.dataKey, data)
                            dataFragment.arguments = bundle

                            val tag = dataFragment.toString()
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.detail_fragment_container,
                                    dataFragment,
                                    tag
                                )
                                .commit()
                        }
                        is Resource.Error -> {
                        }
                    }
                }
            }
        }
    }
}