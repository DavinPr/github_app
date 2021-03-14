package com.app.githubmobile.detail

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.app.githubmobile.R
import com.app.githubmobile.databinding.ActivityDetailBinding
import com.app.githubmobile.detail.datadisplay.DetailDataFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.scope.emptyState

class DetailActivity : AppCompatActivity() {

    companion object {
        const val dataKey = "data_key"
        const val FRAGMENT_RESULT = "fragment_result"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel(state = emptyState())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(dataKey)

        if (savedInstanceState == null) {
            val dataFragment = DetailDataFragment()
            val bundle = Bundle()
            bundle.putString(DetailDataFragment.dataKey, username)
            dataFragment.arguments = bundle

            val tag = DetailDataFragment::class.java.simpleName
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.detail_fragment_container,
                    dataFragment,
                    tag
                )
                .commit()

            viewModel.putDetailFragmentTag(tag)
        } else {
            supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_RESULT)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        val fragment = supportFragmentManager.findFragmentByTag(viewModel.getDetailFragmentTag())
        if (fragment != null) {
            supportFragmentManager.putFragment(outState, FRAGMENT_RESULT, fragment)
        }
    }
}