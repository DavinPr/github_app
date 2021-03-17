package com.app.githubmobile.detail.userfollow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.githubmobile.R
import com.app.githubmobile.databinding.FragmentUserFollowBinding
import com.app.githubmobile.detail.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFollowFragment : Fragment() {

    private var _binding: FragmentUserFollowBinding? = null
    private val binding get() = _binding!!
    private val viewModel by sharedViewModel<DetailViewModel>()

    companion object {
        const val USERNAME_KEY = "username_key"
        const val TAB_KEY = "tab_key"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME_KEY)
        val indexSelected = arguments?.getInt(TAB_KEY)

        binding.toolbar.apply {
            title = username
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
            setNavigationOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }

        val sectionPagerAdapter =
            username?.let { SectionPagerAdapter((activity as AppCompatActivity), it) }
        val viewPager = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabLayout = binding.tabLayout
        tabLayout.post {
            if (indexSelected != null) {
                tabLayout.getTabAt(indexSelected)?.select()
            }
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

//    override fun onResume() {
//        super.onResume()
//        this.tag?.let { viewModel.putDetailFragmentTag(it) }
//    }
}