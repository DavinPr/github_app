package com.app.githubmobile.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import com.app.coremodule.domain.usecase.model.User
import com.app.githubmobile.R
import com.app.githubmobile.databinding.ActivityDetailBinding
import com.app.githubmobile.helper.setImage
import com.app.githubmobile.helper.shortNumberDisplay
import com.google.android.material.appbar.AppBarLayout
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.abs
import kotlin.math.roundToInt

class DetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener,
    View.OnClickListener {

    companion object {
        const val dataKey = "data_key"

        const val SWITCH_BOUND = 0.8f
        const val TO_EXPANDED = 0
        const val TO_COLLAPSED = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var toolbar: Toolbar

    private val viewModel: DetailViewModel by viewModel()

    private var avatarExpandSize = 0f
    private var avatarCollapseSize = 0f
    private var btntnMargin = 0f
    private var horizontalToolbarAvatarMargin: Float = 0F

    private var cashCollapseState: Pair<Int, Int>? = null

    private var avatarAnimateStartPointY: Float = 0F
    private var avatarCollapseAnimationChangeWeight: Float = 0F
    private var isCalculated = false
    private var verticalToolbarAvatarMargin = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.toolbar.root
        setSupportActionBar(toolbar)
        supportActionBar?.title = null

        val data: User? = intent.getParcelableExtra(dataKey)

        if (data != null) {
            dataBinding(data)
        }

        avatarExpandSize = resources.getDimension(R.dimen.default_expanded_image_size)
        avatarCollapseSize = resources.getDimension(R.dimen.default_collapsed_image_size)
        btntnMargin = resources.getDimension(R.dimen.button_margin)
        horizontalToolbarAvatarMargin = resources.getDimension(R.dimen.default_horizontal_margin)

        binding.appbar.addOnOffsetChangedListener(this)
        binding.toolbar.apply {
            btnFavorite.also {
                it.setOnClickListener { view ->
                    if (it.getState()) {
                        /* delete */
                        if (data != null) {
                            viewModel.deleteFavorite(data)
                        }
                    } else {
                        /* insert */
                        if (data != null) {
                            viewModel.insertFavorite(data)
                        }
                    }
                    it.setState(!it.getState())
                }
            }
            btnBack.setOnClickListener(this@DetailActivity)
        }
    }

    private fun dataBinding(user: User) {
        user.avatar?.let { setImage(this, binding.detailAvatar, it) }
        binding.dataContainer.apply {
            detailName.text = user.name
            detailUsername.text = user.username
            val followers = SpannableStringBuilder()
                .bold { append(user.follower?.shortNumberDisplay().toString()) }
                .append(" ${resources.getString(R.string.followers)}")
            detailFollowers.text = followers

            val following = SpannableStringBuilder()
                .bold { append(user.following?.shortNumberDisplay().toString()) }
                .append(" ${resources.getString(R.string.following)}")
            detailFollowing.text = following

            detailCompany.text = user.company
            val repo = SpannableStringBuilder()
                .bold { append(user.repository?.shortNumberDisplay().toString()) }
                .append(" ${resources.getString(R.string.repository)}")
            detailRepository.text = repo
        }
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

    private fun updateViews(offset: Float) {
        /** collapse - expand switch*/
        when {
            offset < SWITCH_BOUND -> Pair(TO_EXPANDED, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
            else -> Pair(TO_COLLAPSED, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
        }.apply {
            when {
                cashCollapseState != null && cashCollapseState != this -> {
                    when (first) {
                        TO_EXPANDED -> {
                            /* set avatar on start position (center of parent frame layout)*/
                            binding.detailAvatar.translationX = 0F
                            binding.toolbar.btnBack.apply {
                                background.alpha = 255
                                imageTintList =
                                    ContextCompat.getColorStateList(
                                        this@DetailActivity,
                                        R.color.white
                                    )
                            }
                        }
                        TO_COLLAPSED -> {
                            binding.toolbar.btnBack.apply {
                                background.alpha = 0
                                imageTintList =
                                    ContextCompat.getColorStateList(
                                        this@DetailActivity,
                                        R.color.black
                                    )
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

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.toolbar.btnBack.id -> finish()
        }
    }
}