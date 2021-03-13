package com.app.coremodule.domain.usecase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(
    val twitterUsername: String? = null,
    val bio: String? = null,
    val login: String? = null,
    val blog: String? = null,
    val company: String? = null,
    val id: Int? = null,
    val publicRepos: Int? = null,
    val email: String? = null,
    val publicGists: Int? = null,
    val followers: Int? = null,
    val avatar: String? = null,
    val htmlUrl: String? = null,
    val following: Int? = null,
    val name: String? = null,
    val location: String? = null
) : Parcelable
