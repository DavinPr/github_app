package com.app.consumerapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name : String? = null,
    val username: String,
    val avatar: String?,
    val id: Int = 0
) : Parcelable
