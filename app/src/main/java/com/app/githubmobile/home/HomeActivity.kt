package com.app.githubmobile.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.githubmobile.R
import com.app.githubmobile.databinding.ActivityHomeBinding
import com.app.githubmobile.home.dashboard.DashboardFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    companion object {
        const val FRAGMENT_RESULT = "fragment_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            val tag = DashboardFragment::class.java.simpleName
            supportFragmentManager
                .beginTransaction()
                .add(R.id.home_container, DashboardFragment(), tag)
                .commit()
        } else {
            supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_RESULT)
        }

    }
}