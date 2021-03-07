package com.app.coremodule.domain.usecase.model

data class User(
    val follower: Int? = null,
    val following: Int? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val avatar: String? = null,
    val repository: Int? = null,
    val username: String
)