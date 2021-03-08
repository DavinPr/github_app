package com.app.githubmobile.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import com.app.coremodule.domain.usecase.model.User
import com.app.githubmobile.R
import com.app.githubmobile.databinding.ActivityDetailBinding
import com.app.githubmobile.helper.setImage
import com.app.githubmobile.helper.shortNumberDisplay
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs
import kotlin.math.roundToInt

class DetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    companion object {
        const val dataKey = "data_key"

        const val SWITCH_BOUND = 0.8f
        const val TO_EXPANDED = 0
        const val TO_COLLAPSED = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
    }

    private lateinit var binding: ActivityDetailBinding

    private var avatarExpandSize = 0f
    private var avatarCollapseSize = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: User? = intent.getParcelableExtra(dataKey)

        if (data != null) {
            dataBinding(data)
        }

        avatarExpandSize = resources.getDimension(R.dimen.default_expanded_image_size)
        avatarCollapseSize = resources.getDimension(R.dimen.default_collapsed_image_size)

        binding.appbar.addOnOffsetChangedListener(this)
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

    private var horizontalToolbarAvatarMargin: Float = 0F
    private var cashCollapseState: Pair<Int, Int>? = null

    private var avatarAnimateStartPointY: Float = 0F
    private var avatarCollapseAnimationChangeWeight: Float = 0F
    private var isCalculated = false
    private var verticalToolbarAvatarMargin = 0F

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (isCalculated.not()) {
            avatarAnimateStartPointY =
                abs((appBarLayout.height - avatarExpandSize - binding.toolbar.height) / appBarLayout.totalScrollRange)
            avatarCollapseAnimationChangeWeight = 1 / (1 - avatarAnimateStartPointY)
            verticalToolbarAvatarMargin = (binding.toolbar.height - avatarCollapseSize) * 2
            isCalculated = true
        }
        updateViews(abs(verticalOffset / appBarLayout.totalScrollRange.toFloat()))
        Log.d("avatarY", avatarAnimateStartPointY.toString())
    }

    private fun updateViews(offset: Float) {
        /* apply levels changes*/
        when (offset) {
            in 0.15F..1F -> {
            }

            in 0F..0.15F -> {
                binding.detailAvatar.alpha = 1f
            }
        }

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
                            /* set avatar on start position (center of parent frame layout)*/
//                            binding.detailAvatar.apply {
//                                animate().setDuration(250).alpha(1f)
//                            }
                            binding.btnBack.alpha = 1f
                        }
                        TO_COLLAPSED -> {
//                            binding.detailAvatar.apply {
//                                animate().setDuration(250).alpha(0f)
//                            }
                            binding.btnBack.alpha = 0f
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

                        this.translationX =
                            ((binding.appbar.width - horizontalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
                        this.translationY =
                            ((binding.toolbar.height - verticalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
                    }
                    else -> this.layoutParams.also {
                        if (it.height != avatarExpandSize.toInt()) {
                            it.height = avatarExpandSize.toInt()
                            it.width = avatarExpandSize.toInt()
                            this.layoutParams = it
                        }
                        translationX = 0f
                        this.alpha = 1f
                    }
                }
            }
        }
    }
}