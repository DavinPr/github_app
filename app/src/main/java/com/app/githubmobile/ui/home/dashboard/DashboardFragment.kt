package com.app.githubmobile.ui.home.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubmobile.R
import com.app.githubmobile.customview.DividerItemDecorationWithoutLast
import com.app.githubmobile.databinding.FragmentDashboardBinding
import com.app.githubmobile.helper.setLocale
import com.app.githubmobile.ui.detail.DetailActivity
import com.app.githubmobile.ui.favorite.FavoriteActivity
import com.app.githubmobile.ui.home.HomeActivity
import com.app.githubmobile.ui.home.HomeViewModel
import com.app.githubmobile.ui.home.search.SearchFragment
import com.app.githubmobile.ui.setting.SettingsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class DashboardFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()

    companion object {
        private const val SETTING_REQUEST = 100
        const val SETTING_RESULT = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(requireActivity(), viewModel.getLanguage.toString())
        super.onCreate(savedInstanceState)
    }

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

        val recentAdapter = RecentListAdapter()
        viewModel.getAllRecent().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.recentLayout.apply {
                    rvRecent.visibility = View.GONE
                    recentEmpty.root.visibility = View.VISIBLE
                }
            } else {
                binding.recentLayout.apply {
                    rvRecent.visibility = View.VISIBLE
                    recentEmpty.root.visibility = View.GONE
                }
                recentAdapter.setData(it)
            }
        }

        recentAdapter.onClickItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.dataKey, it)
            startActivity(intent)
        }

        binding.recentLayout.rvRecent.apply {
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
            adapter = recentAdapter
            addItemDecoration(
                DividerItemDecorationWithoutLast(requireContext())
            )
        }

        binding.btnFavorite.setOnClickListener(this)
        binding.btnSetting.setOnClickListener(this)
        binding.search.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SETTING_REQUEST) {
            if (resultCode == SETTING_RESULT) {
                val frg = this
                val ft = parentFragmentManager.beginTransaction()
                ft.detach(frg)
                ft.attach(frg)
                ft.commit()
            }
        }
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
            binding.btnSetting.id -> {
                val intent = Intent(activity, SettingsActivity::class.java)
                startActivityForResult(intent, SETTING_REQUEST)
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