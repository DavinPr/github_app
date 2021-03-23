package com.app.githubmobile.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.githubmobile.R
import com.app.githubmobile.databinding.ActivityDetailBinding
import com.app.githubmobile.ui.detail.datadisplay.DetailDataFragment

class DetailActivity : AppCompatActivity() {

    companion object {
        const val dataKey = "data_key"
        const val FRAGMENT_RESULT = "fragment_result"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
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
            supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_RESULT)
        }
    }
}