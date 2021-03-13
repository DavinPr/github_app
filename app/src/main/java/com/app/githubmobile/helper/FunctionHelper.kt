package com.app.githubmobile.helper

import java.math.RoundingMode
import java.text.DecimalFormat
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