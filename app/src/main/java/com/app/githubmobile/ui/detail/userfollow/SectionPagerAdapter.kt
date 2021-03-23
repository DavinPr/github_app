package com.app.githubmobile.ui.detail.userfollow

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.githubmobile.ui.detail.userfollow.followers.FollowersFragment
import com.app.githubmobile.ui.detail.userfollow.following.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return fragment as Fragment
    }
}