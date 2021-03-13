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
import com.app.githubmobile.home.HomeViewModel
import com.app.githubmobile.home.search.SearchFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class DashboardFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

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
                val mCategoryFragment = SearchFragment()
                val mFragmentManager = fragmentManager
                val tag = SearchFragment::class.java.simpleName
                mFragmentManager?.beginTransaction()?.apply {
                    replace(
                        R.id.home_container,
                        mCategoryFragment,
                        tag
                    )
                    addToBackStack(null)
                    commit()
                }
                viewModel.putFragmentTag(tag)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.putFragmentTag(DashboardFragment::class.java.simpleName)
    }
}