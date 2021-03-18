package com.app.coremodule.domain.usecase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(
    val bio: String? = null,
    val login: String? = null,
    val blog: String? = null,
    val company: String? = null,
    val id: Int = 0,
    val publicRepos: Int = 0,
    val publicGists: Int = 0,
    val followers: Int = 0,
    val avatar: String? = null,
    val htmlUrl: String? = null,
    val following: Int = 0,
    val name: String? = null,
    val location: String? = null,
    var isFavorite: Boolean = false
) : Parcelable
