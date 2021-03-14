package com.app.githubmobile.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton
import com.app.githubmobile.R

class CustomToggleButton
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.buttonStyleToggle
) : AppCompatToggleButton(context, attrs, defStyleAttr) {

    private var iconOn: Drawable? = null
    private var iconOff: Drawable? = null

    init {
        attrs?.let { retrieveAttributes(it) }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if (isChecked) {
            iconOn
        } else {
            iconOff
        }
    }

    private fun retrieveAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToggleButton)
        iconOn = typedArray.getDrawable(R.styleable.CustomToggleButton_ctb_iconOn)
        iconOff = typedArray.getDrawable(R.styleable.CustomToggleButton_ctb_iconOff)
        typedArray.recycle()
    }
}