package com.app.githubmobile.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.app.githubmobile.R

class CustomFavoriteButton : CircularImageButton {

    private var state: Boolean = false

    private var iconEnable: Drawable? = null
    private var iconDisable: Drawable? = null
    private var backgroundTintEnable: ColorStateList? = null
    private var backgroundTintDisable: ColorStateList? = null
    private var iconTintEnable: ColorStateList? = null
    private var iconTintDisable: ColorStateList? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomFavoriteButton, defStyleAttr, 0)
        state = typedArray.getBoolean(R.styleable.CustomFavoriteButton_state, false)
        typedArray.recycle()
    }

    fun setState(state: Boolean) {
        this.state = state
    }

    fun getState(): Boolean = state

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (state) {
            setImageDrawable(iconEnable)
            backgroundTintList = backgroundTintEnable
            imageTintList = iconTintEnable
        } else {
            setImageDrawable(iconDisable)
            backgroundTintList = backgroundTintDisable
            imageTintList = iconTintDisable
        }
    }

    private fun init() {
        iconEnable = ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, null)
        iconDisable = ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_border, null)
        backgroundTintEnable = ContextCompat.getColorStateList(context, R.color.red_light)
        backgroundTintDisable = ContextCompat.getColorStateList(context, R.color.light_gray)
        iconTintEnable = ContextCompat.getColorStateList(context, R.color.red)
        iconTintDisable = ContextCompat.getColorStateList(context, R.color.white)
    }
}