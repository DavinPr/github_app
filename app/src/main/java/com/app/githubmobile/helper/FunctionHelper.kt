package com.app.githubmobile.helper

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

fun setImage(context: Context, view: ImageView, file: String) {
    context.apply {
        val image = resources.getIdentifier(file, "drawable", packageName)
        Glide.with(this)
            .load(image)
            .into(view)
    }
}

fun Double.shortNumberDisplay(): String {
    val suffixChars = "KMGTPE"
    val formatter = DecimalFormat("###.#")
    formatter.roundingMode = RoundingMode.DOWN

    return if (this < 1000.0) formatter.format(this)
    else {
        val exp = (ln(this) / ln(1000.0)).toInt()
        formatter.format(this / 1000.0.pow(exp.toDouble())) + suffixChars[exp - 1]
    }
}