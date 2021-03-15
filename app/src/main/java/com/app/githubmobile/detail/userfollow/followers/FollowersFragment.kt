package com.app.githubmobile.detail.userfollow.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.githubmobile.databinding.FragmentFollowersBinding
import com.app.githubmobile.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val ARG_USERNAME = "username"

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private val viewModel by sharedViewModel<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)
        binding.tvFollowers.text = "Followers $username"
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }
}