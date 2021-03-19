package com.app.githubmobile.home.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.coremodule.data.Resource
import com.app.githubmobile.adapter.UserListAdapter
import com.app.githubmobile.databinding.FragmentSearchBinding
import com.app.githubmobile.detail.DetailActivity
import com.app.githubmobile.home.HomeActivity
import com.app.githubmobile.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by sharedViewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAdapter = UserListAdapter()

        binding.searchBox.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    lifecycleScope.launch {
                        if (newText != null) {
                            if (newText.trim().isNotEmpty()) {
                                binding.rvUsers.visibility = View.GONE
                                val searchText = "Searching for \"$newText\""
                                binding.tvSearch.apply {
                                    text = searchText
                                    visibility = View.VISIBLE
                                }
                                viewModel.queryChannel.send(newText)
                            } else {
                                binding.tvSearch.visibility = View.GONE
                                binding.rvUsers.visibility = View.GONE
                            }
                        }
                    }
                    return false
                }
            })
        }

        viewModel.searchResult.observe(viewLifecycleOwner) { user ->
            lifecycleScope.launch {
                user.observe(viewLifecycleOwner) { userFlow ->
                    when (userFlow) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            binding.tvSearch.visibility = View.GONE
                            val data = userFlow.data
                            if (data != null) {
                                binding.rvUsers.visibility = View.VISIBLE
                                userAdapter.setData(data)
                            }
                        }
                        is Resource.Error -> {
                            binding.tvSearch.visibility = View.GONE
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        userAdapter.onClickItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.dataKey, it)
            context?.startActivity(intent)
        }

        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            adapter = userAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        binding.btnBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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