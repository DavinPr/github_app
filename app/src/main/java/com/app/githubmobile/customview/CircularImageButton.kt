package com.app.githubmobile.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.app.githubmobile.R

open class CircularImageButton
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.imageButtonStyle
) : AppCompatImageButton(context, attrs, defStyleAttr) {

    private var backgroundSetup: Drawable? = null
    private lateinit var icon: Drawable
    private lateinit var iconTint: ColorStateList
    private lateinit var cibBackgroundTint: ColorStateList

    init {
        backgroundSetup = ResourcesCompat.getDrawable(resources, R.drawable.icon_background, null)
        scaleType = ScaleType.FIT_XY
        attrs?.let { retrieveAttributes(it) }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = backgroundSetup
        backgroundTintList = cibBackgroundTint
        setImageDrawable(icon)
        imageTintList = iconTint
    }

    private fun retrieveAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularImageButton)
        icon = typedArray.getDrawable(R.styleable.CircularImageButton_cib_icon)!!
        iconTint = typedArray.getColorStateList(R.styleable.CircularImageButton_cib_iconTint)
            ?: ContextCompat.getColorStateList(context, R.color.white)!!
        cibBackgroundTint =
            typedArray.getColorStateList(R.styleable.CircularImageButton_cib_backgroundTint)
                ?: ContextCompat.getColorStateList(context, R.color.gray)!!
        typedArray.recycle()
    }

}