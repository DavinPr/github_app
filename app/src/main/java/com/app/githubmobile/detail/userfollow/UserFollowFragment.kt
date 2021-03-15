package com.app.githubmobile.detail.userfollow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.githubmobile.R
import com.app.githubmobile.databinding.FragmentUserFollowBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserFollowFragment : Fragment() {

    private var _binding: FragmentUserFollowBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val USERNAME_KEY = "username_key"

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


        val sectionPagerAdapter =
            username?.let { SectionPagerAdapter((activity as AppCompatActivity), it) }
        val viewPager = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.btnClose.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}