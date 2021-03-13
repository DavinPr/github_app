package com.app.githubmobile.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.app.githubmobile.R
import com.app.githubmobile.databinding.ActivityHomeBinding
import com.app.githubmobile.home.dashboard.DashboardFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()

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
            viewModel.putFragmentTag(tag)
        } else {
            supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_RESULT)
        }

    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        val fragment = supportFragmentManager.findFragmentByTag(viewModel.getFragmentTag)
        if (fragment != null) {
            supportFragmentManager.putFragment(outState, FRAGMENT_RESULT, fragment)
        }
    }
}