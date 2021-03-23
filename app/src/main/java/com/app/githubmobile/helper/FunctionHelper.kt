package com.app.githubmobile.helper

import android.app.Activity
import android.content.res.Configuration
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.math.ln
import kotlin.math.pow

fun Int.shortNumberDisplay(): String {
    val dblNum = this.toDouble()
    val suffixChars = "KMBTPE"
    val formatter = DecimalFormat("###.#")
    formatter.roundingMode = RoundingMode.DOWN

    return if (this < 1000.0) formatter.format(dblNum)
    else {
        val exp = (ln(dblNum) / ln(1000.0)).toInt()
        formatter.format(this / 1000.0.pow(exp.toDouble())) + suffixChars[exp - 1]
    }
}

@Suppress("DEPRECATION")
fun setLocale(activity: Activity, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration()
    activity.resources.updateConfiguration(config, activity.resources.displayMetrics)
}