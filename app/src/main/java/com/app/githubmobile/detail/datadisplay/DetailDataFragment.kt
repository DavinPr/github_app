package com.app.githubmobile.detail.datadisplay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.model.Detail
import com.app.githubmobile.R
import com.app.githubmobile.databinding.FragmentDetailDataBinding
import com.app.githubmobile.detail.DetailActivity
import com.app.githubmobile.detail.DetailViewModel
import com.app.githubmobile.detail.userfollow.UserFollowFragment
import com.app.githubmobile.helper.shortNumberDisplay
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.abs
import kotlin.math.roundToInt

class DetailDataFragment : Fragment(), AppBarLayout.OnOffsetChangedListener,
    View.OnClickListener {

    private var _binding: FragmentDetailDataBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val dataKey = "data_key"

        const val SWITCH_BOUND = 0.8f
        const val TO_EXPANDED = 0
        const val TO_COLLAPSED = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
    }

    private lateinit var toolbar: Toolbar
    private var username: String? = null
    private var _detail: Detail? = null
    private val detail get() = _detail!!

    private val viewModel: DetailViewModel by sharedViewModel()

    private var avatarExpandSize = 0f
    private var avatarCollapseSize = 0f
    private var btntnMargin = 0f
    private var horizontalToolbarAvatarMargin: Float = 0F

    private var cashCollapseState: Pair<Int, Int>? = null

    private var avatarAnimateStartPointY: Float = 0F
    private var avatarCollapseAnimationChangeWeight: Float = 0F
    private var isCalculated = false
    private var isCollapsed = false
    private var verticalToolbarAvatarMargin = 0F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = binding.toolbar.root
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.title = null
        }

        avatarExpandSize = resources.getDimension(R.dimen.default_expanded_image_size)
        avatarCollapseSize = resources.getDimension(R.dimen.default_collapsed_image_size)
        btntnMargin = resources.getDimension(R.dimen.button_margin)
        horizontalToolbarAvatarMargin = resources.getDimension(R.dimen.default_horizontal_margin)

        val username = arguments?.getString(dataKey)

        if (username != null) {
            this.username = username
            viewModel.getDetailData(username).observe(viewLifecycleOwner) { detail ->
                when (detail) {
                    is Resource.Loading -> {
                        binding.detailLoading.visibility = View.VISIBLE
                        binding.coordinatorLayout.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.coordinatorLayout.visibility = View.VISIBLE
                        binding.detailLoading.visibility = View.GONE
                        val data = detail.data
                        if (data != null) {
                            if (_detail == null) {
                                _detail = data
                                dataBinding(data)
                            } else {
                                dataBinding(this.detail)
                            }
                        }
                    }
                    is Resource.Error -> {
                        binding.detailLoading.visibility = View.GONE
                    }
                }
            }
        }

        binding.appbar.addOnOffsetChangedListener(this)
        binding.toolbar.apply {
            btnBack.setOnClickListener(this@DetailDataFragment)
            btnFavorite.setOnClickListener(this@DetailDataFragment)
        }
        binding.dataContainer.apply {
            detailFollowers.setOnClickListener(this@DetailDataFragment)
            detailFollowing.setOnClickListener(this@DetailDataFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (activity as AppCompatActivity).supportFragmentManager.putFragment(
            outState,
            DetailActivity.FRAGMENT_RESULT,
            this
        )
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (isCalculated.not()) {
            avatarAnimateStartPointY =
                abs((appBarLayout.height - (avatarExpandSize + toolbar.height)) / appBarLayout.totalScrollRange)
            avatarCollapseAnimationChangeWeight = 1 / (1 - avatarAnimateStartPointY)
            verticalToolbarAvatarMargin = (toolbar.height - avatarCollapseSize) * 2
            isCalculated = true
        }
        updateViews(abs(verticalOffset / appBarLayout.totalScrollRange.toFloat()))
    }

    override fun onClick(v: View?) {
        val dataContainer = binding.dataContainer
        when (v?.id) {
            binding.toolbar.btnBack.id -> activity?.finish()

            binding.toolbar.btnFavorite.id -> {
                val state = !detail.isFavorite
                if (state) {
                    viewModel.insertFavorite(detail)
                } else {
                    viewModel.deleteFavorite(detail)
                }
                detail.isFavorite = state
                setCheckedState(state, binding.toolbar.btnFavorite)
            }

            dataContainer.detailFollowers.id,
            dataContainer.detailFollowing.id -> {
                val selectedTab = if (v.id == dataContainer.detailFollowers.id) 0 else 1
                if (isCollapsed) {
                    binding.appbar.setExpanded(true)

                    Handler(Looper.getMainLooper()).postDelayed({
                        goToUserFollow(selectedTab)
                    }, 300)
                } else {
                    goToUserFollow(selectedTab)
                }
            }
        }
    }

    private fun goToUserFollow(selected: Int) {
        val mUserFollowFragment = UserFollowFragment()
        val bundle = Bundle()
        bundle.putString(UserFollowFragment.USERNAME_KEY, this.username)
        bundle.putInt(UserFollowFragment.TAB_KEY, selected)
        mUserFollowFragment.arguments = bundle

        val mFragmentManager = activity?.supportFragmentManager
        val tag = UserFollowFragment::class.java.simpleName
        mFragmentManager?.beginTransaction()?.apply {
            replace(
                R.id.detail_fragment_container,
                mUserFollowFragment,
                tag
            )
            addToBackStack(null)
            commit()
        }
    }

    private fun dataBinding(detail: Detail) {
        Glide.with(this)
            .load(detail.avatar)
            .into(binding.detailAvatar)

        binding.toolbar.btnFavorite.apply {
            setCheckedState(detail.isFavorite, this)
        }

        binding.dataContainer.apply {
            detailName.text = detail.name ?: detailName.text
            detailUsername.text = detail.login

            val followers = SpannableStringBuilder()
                .bold { append(detail.followers.shortNumberDisplay()) }
                .append(" ${resources.getString(R.string.followers)}")
            detailFollowers.text = followers

            val following = SpannableStringBuilder()
                .bold { append(detail.following.shortNumberDisplay()) }
                .append(" ${resources.getString(R.string.following)}")
            detailFollowing.text = following

            personalDataContainer.apply {
                detail.bio.also { displayTextView(detailBio, it) }
                detail.company.also { displayTextView(detailCompany, it) }
                detail.blog.also {
                    displayTextView(detailBlog, it)
                    if (it != null) {
                        detailBlog.setOnClickListener { _ ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            startActivity(intent)
                        }
                    }
                }
                detail.location.also { displayTextView(detailLocation, it) }

                btnOpenInGithub.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detail.htmlUrl))
                    startActivity(intent)
                }
            }

            gitDataContainer.apply {
                detailRepository.text = detail.publicRepos.shortNumberDisplay()
                detailStarred.text = (0).shortNumberDisplay()
                detailGist.text = detail.publicGists.shortNumberDisplay()
            }
        }
    }

    private fun displayTextView(view: TextView, data: String?) {
        if (data.isNullOrEmpty()) {
            view.visibility = View.GONE
        } else {
            view.text = data
        }
    }

    private fun updateViews(offset: Float) {
        /** collapse - expand switch*/
        when {
            offset < SWITCH_BOUND -> Pair(
                TO_EXPANDED,
                cashCollapseState?.second ?: WAIT_FOR_SWITCH
            )
            else -> Pair(
                TO_COLLAPSED,
                cashCollapseState?.second ?: WAIT_FOR_SWITCH
            )
        }.apply {
            when {
                cashCollapseState != null && cashCollapseState != this -> {
                    when (first) {
                        TO_EXPANDED -> {
                            /* set avatar on start position (center of parent frame layout)*/
                            isCollapsed = false
                            binding.detailAvatar.translationX = 0F
                            binding.detailAvatar.borderColor =
                                ContextCompat.getColor(requireContext(), R.color.gray)
                            (activity as AppCompatActivity).window.apply {
                                statusBarColor =
                                    ContextCompat.getColor(requireContext(), R.color.white)
                            }
                            binding.toolbar.apply {
                                root.background = null
                            }
                            binding.toolbar.btnFavorite.apply {
                                imageTintList = (ContextCompat.getColorStateList(
                                    requireContext(),
                                    R.color.gray
                                ))
                            }
                        }
                        TO_COLLAPSED -> {
                            isCollapsed = true
                            binding.detailAvatar.borderColor =
                                ContextCompat.getColor(requireContext(), R.color.white)
                            (activity as AppCompatActivity).window.statusBarColor =
                                ContextCompat.getColor(requireContext(), R.color.gray)
                            binding.toolbar.apply {
                                root.background =
                                    ContextCompat.getDrawable(requireContext(), R.color.gray)
                            }
                            binding.toolbar.btnFavorite.apply {
                                imageTintList = (ContextCompat.getColorStateList(
                                    requireContext(),
                                    R.color.white
                                ))
                            }
                        }
                    }
                    cashCollapseState = Pair(first, SWITCHED)
                }
                else -> {
                    cashCollapseState = Pair(first, WAIT_FOR_SWITCH)
                }
            }

            /* Collapse avatar img*/
            binding.detailAvatar.apply {
                when {
                    offset > avatarAnimateStartPointY -> {

                        val avatarCollapseAnimateOffset =
                            (offset - avatarAnimateStartPointY) * avatarCollapseAnimationChangeWeight
                        val avatarSize =
                            avatarExpandSize - (avatarExpandSize - avatarCollapseSize) * avatarCollapseAnimateOffset
                        this.layoutParams.also {
                            it.height = avatarSize.roundToInt()
                            it.width = avatarSize.roundToInt()
                            this.layoutParams = it
                        }

                        val favoriteBtnStartPosition =
                            (binding.toolbar.btnFavorite.width + btntnMargin) * 2

                        this.translationX =
                            ((binding.appbar.width - (horizontalToolbarAvatarMargin + avatarSize + favoriteBtnStartPosition)) / 2) * avatarCollapseAnimateOffset
                        this.translationY =
                            ((toolbar.height - (verticalToolbarAvatarMargin + avatarSize)) / 2) * avatarCollapseAnimateOffset
                    }
                    else -> this.layoutParams.also {
                        if (it.height != avatarExpandSize.toInt()) {
                            it.height = avatarExpandSize.toInt()
                            it.width = avatarExpandSize.toInt()
                            this.layoutParams = it
                        }
                        translationX = 0f
                    }
                }
            }
        }
    }

    private fun setCheckedState(state: Boolean, btn: ImageButton) {
        btn.apply {
            if (state) {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_favorite
                    )
                )
            } else {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_favorite_border
                    )
                )
            }
        }
    }
}