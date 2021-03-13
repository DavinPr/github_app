package com.app.coremodule.domain.usecase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name : String? = null,
    val username: String,
    val avatar: String?,
    val id: Int = 0
) : Parcelable
