package com.app.githubmobile.customview

import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs
import kotlin.math.roundToInt

class CustomAppbarOffset(
    private var toolbar: Toolbar,
    private var ivUserAvatar: ImageView,
    private var btnBack: ImageButton,
    private var appBarLayout: AppBarLayout
) :
    AppBarLayout.OnOffsetChangedListener {

    private var EXPAND_AVATAR_SIZE: Float = 0F
    private var COLLAPSE_IMAGE_SIZE: Float = 0F
    private var horizontalToolbarAvatarMargin: Float = 0F
    private var cashCollapseState: Pair<Int, Int>? = null

    private var avatarAnimateStartPointY: Float = 0F
    private var avatarCollapseAnimationChangeWeight: Float = 0F
    private var isCalculated = false
    private var verticalToolbarAvatarMargin = 0F

    /* set data avatar */
    fun setAvatarSize(expandSize: Float, collpaseSize: Float) {
        this.EXPAND_AVATAR_SIZE = expandSize
        this.COLLAPSE_IMAGE_SIZE = collpaseSize
    }


    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (isCalculated.not()) {
            avatarAnimateStartPointY =
                abs((appBarLayout.height - EXPAND_AVATAR_SIZE) / appBarLayout.totalScrollRange)
            avatarCollapseAnimationChangeWeight = 1 / (1 - avatarAnimateStartPointY)
            verticalToolbarAvatarMargin = (toolbar.height - COLLAPSE_IMAGE_SIZE) * 2
            isCalculated = true
        }
        updateViews(abs(verticalOffset / appBarLayout.totalScrollRange.toFloat()))
    }

    private fun updateViews(offset: Float) {
        /* apply levels changes*/
        when (offset) {
            in 0.15F..1F -> {
            }

            in 0F..0.15F -> {
                ivUserAvatar.alpha = 1f
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
                            ivUserAvatar.translationX = 0F
                            /* set avatar on start position (center of parent frame layout)*/
//                            ivUserAvatar.apply {
//                                animate().setDuration(250).alpha(1f)
//                            }
                            btnBack.alpha = 1f
                        }
                        TO_COLLAPSED -> {
//                            ivUserAvatar.apply {
//                                animate().setDuration(250).alpha(0f)
//                            }
                            btnBack.alpha = 0f
                        }
                    }
                    cashCollapseState = Pair(first, SWITCHED)
                }
                else -> {
                    cashCollapseState = Pair(first, WAIT_FOR_SWITCH)
                }
            }

            /* Collapse avatar img*/
            ivUserAvatar.apply {
                when {
                    offset > avatarAnimateStartPointY -> {
                        this.alpha = 0f
//                        val avatarCollapseAnimateOffset =
//                            (offset - avatarAnimateStartPointY) * avatarCollapseAnimationChangeWeight
//                        val avatarSize =
//                            EXPAND_AVATAR_SIZE - (EXPAND_AVATAR_SIZE - COLLAPSE_IMAGE_SIZE) * avatarCollapseAnimateOffset
//                        this.layoutParams.also {
//                            it.height = 25
//                            it.width = 25
//                        }
//
//                        this.translationX =
//                            ((appBarLayout.width - horizontalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
//                        this.translationY =
//                            ((toolbar.height - verticalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
                    }
                    else -> this.layoutParams.also {
                        if (it.height != EXPAND_AVATAR_SIZE.toInt()) {
                            it.height = EXPAND_AVATAR_SIZE.toInt()
                            it.width = EXPAND_AVATAR_SIZE.toInt()
                            this.layoutParams = it
                        }
                        translationX = 0f
                        this.alpha = 1f
                    }
                }
            }
        }
    }

    companion object {
        const val SWITCH_BOUND = 0.8f
        const val TO_EXPANDED = 0
        const val TO_COLLAPSED = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
    }

}