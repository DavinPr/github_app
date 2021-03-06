package com.app.coremodule.utils

import com.app.coremodule.data.remote.response.UsersItem
import com.app.coremodule.domain.usecase.model.User

object DataMapper {
    fun mapResponseToDomain(userResponse: List<UsersItem>): List<User> {
        val list = ArrayList<User>()
        userResponse.map {
            val user = User(
                follower = it.follower,
                following = it.following,
                name = it.name,
                company = it.company,
                location = it.location,
                avatar = it.avatar,
                repository = it.repository,
                username = it.username
            )
            list.add(user)
        }
        return list
    }
}