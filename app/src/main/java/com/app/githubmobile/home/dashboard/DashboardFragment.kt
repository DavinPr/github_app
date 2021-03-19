package com.app.githubmobile.home.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.githubmobile.R
import com.app.githubmobile.databinding.FragmentDashboardBinding
import com.app.githubmobile.favorite.FavoriteActivity
import com.app.githubmobile.home.HomeActivity
import com.app.githubmobile.home.search.SearchFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class DashboardFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.btnFavorite.setOnClickListener(this)
        binding.search.setOnClickListener(this)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnFavorite.id -> {
                val intent = Intent(activity, FavoriteActivity::class.java)
                startActivity(intent)
            }
            binding.search.id -> {
                val mSearchFragment = SearchFragment()
                val mFragmentManager = activity?.supportFragmentManager
                val tag = SearchFragment::class.java.simpleName
                mFragmentManager?.beginTransaction()?.apply {
                    replace(
                        R.id.home_container,
                        mSearchFragment,
                        tag
                    )
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (activity as AppCompatActivity).supportFragmentManager.putFragment(
            outState,
            HomeActivity.FRAGMENT_RESULT,
            this
        )
    }
}