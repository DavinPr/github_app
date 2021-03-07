package com.app.githubmobile.helper

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun setImage(context: Context, view: ImageView, file: String) {
    context.apply {
        val image = resources.getIdentifier(file, "drawable", packageName)
        Glide.with(this)
            .load(image)
            .into(view)
    }
}