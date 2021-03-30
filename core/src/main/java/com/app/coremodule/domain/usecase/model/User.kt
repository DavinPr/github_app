package com.app.coremodule.domain.usecase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String? = null,
    val username: String,
    val avatar: String?,
    val id: Int = 0,
    var followers: Int = 0,
    var following: Int = 0
) : Parcelable
