package com.app.coremodule.utils

import com.app.coremodule.data.local.model.FavoriteEntity
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
                username = it.username,
                isFavorited = false
            )
            list.add(user)
        }
        return list
    }

    fun mapEntityToDomain(favoriteEntity: List<FavoriteEntity>): List<User> {
        val list = ArrayList<User>()
        favoriteEntity.map {
            val user = User(
                follower = it.follower,
                following = it.following,
                name = it.name,
                company = it.company,
                location = it.location,
                avatar = it.avatar,
                repository = it.repository,
                username = it.username,
                isFavorited = true
            )
            list.add(user)
        }
        return list
    }

    fun mapDomainToEntity(user: User): FavoriteEntity {
        return FavoriteEntity(
            follower = user.follower,
            following = user.following,
            name = user.name,
            company = user.company,
            location = user.location,
            avatar = user.avatar,
            repository = user.repository,
            username = user.username
        )
    }
}