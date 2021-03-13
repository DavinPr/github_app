package com.app.githubmobile.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageButton
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

    @Px
    private var iconWidth: Int = 0

    @Px
    private var iconHeight: Int = 0

    init {
        backgroundSetup = ResourcesCompat.getDrawable(resources, R.drawable.icon_background, null)
        scaleType = ScaleType.FIT_XY
        attrs?.let { retrieveAttributes(it) }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = backgroundSetup
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = right - left
        val height = bottom - top

        val horizontalPadding = if (width > iconWidth) (width - iconWidth) / 2 else 0
        val verticalPadding = if (height > iconHeight) (height - iconHeight) / 2 else 0

        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        setImageDrawable(icon)
        if (iconWidth == 0) iconWidth = width / 2

        if (iconHeight == 0) iconHeight = height / 2

        super.onLayout(changed, left, top, right, bottom)
    }

    private fun retrieveAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularImageButton)

        icon = typedArray.getDrawable(R.styleable.CircularImageButton_cib_icon)!!

        iconWidth =
            typedArray.getDimension(R.styleable.CircularImageButton_cib_iconWidth, 0f)
                .toInt()
        iconHeight =
            typedArray.getDimension(R.styleable.CircularImageButton_cib_iconHeight, 0f)
                .toInt()

        typedArray.recycle()
    }

}