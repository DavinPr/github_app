package com.app.githubmobile.detail.userfollow.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.githubmobile.databinding.FragmentFollowingBinding
import com.app.githubmobile.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val ARG_USERNAME = "username"

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private val viewModel by sharedViewModel<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)

    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }

}