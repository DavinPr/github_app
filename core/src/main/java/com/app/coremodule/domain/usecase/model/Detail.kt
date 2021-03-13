package com.app.coremodule.domain.usecase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(
    val twitterUsername: String? = null, // not applied
    val bio: String? = null,
    val login: String? = null,
    val blog: String? = null,
    val company: String? = null,
    val id: Int = 0,
    val publicRepos: Int = 0,
    val email: String? = null, // not applied
    val publicGists: Int = 0,
    val followers: Int = 0,
    val avatar: String? = null,
    val htmlUrl: String? = null, // not applied
    val following: Int = 0,
    val name: String? = null,
    val location: String? = null
) : Parcelable
