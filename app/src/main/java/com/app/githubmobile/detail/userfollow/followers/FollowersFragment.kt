package com.app.githubmobile.detail.userfollow.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.coremodule.data.Resource
import com.app.githubmobile.R
import com.app.githubmobile.adapter.UserListAdapter
import com.app.githubmobile.databinding.FragmentFollowersBinding
import com.app.githubmobile.detail.DetailViewModel
import com.app.githubmobile.detail.datadisplay.DetailDataFragment
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
        val userAdapter = UserListAdapter()
        if (username != null) {
            viewModel.getUserFollower(username).observe(viewLifecycleOwner) { users ->
                when (users) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val data = users.data
                        if (data != null) userAdapter.setData(data)
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }

        userAdapter.onClickItem = {
            val mDetailDataFragment = DetailDataFragment()
            val bundle = Bundle()
            bundle.putString(DetailDataFragment.dataKey, it)
            mDetailDataFragment.arguments = bundle

            val mFragmentManager = activity?.supportFragmentManager
            val tag = DetailDataFragment::class.java.simpleName
            mFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.detail_fragment_container,
                    mDetailDataFragment,
                    tag
                )
                addToBackStack(null)
                commit()
            }
            viewModel.putDetailFragmentTag(tag)
        }

        binding.rvFollowers.apply {
            layoutManager = LinearLayoutManager(this@FollowersFragment.requireContext())
            hasFixedSize()
            adapter = userAdapter
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
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