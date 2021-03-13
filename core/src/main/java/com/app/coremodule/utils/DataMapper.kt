package com.app.coremodule.utils

import com.app.coremodule.data.local.model.FavoriteEntity
import com.app.coremodule.data.remote.response.DetailResponse
import com.app.coremodule.data.remote.response.UserItem
import com.app.coremodule.domain.usecase.model.Detail
import com.app.coremodule.domain.usecase.model.User

object DataMapper {

    fun mapUserResponseToDomain(users: List<UserItem>): List<User> {
        val list = ArrayList<User>()
        users.map {
            val user = User(
                avatar = it.avatar,
                username = it.username,
                id = it.id
            )
            list.add(user)
        }
        return list
    }

    fun mapDetailResponseToDomain(detail: DetailResponse): Detail =
        detail.let {
            Detail(
                twitterUsername = it.twitterUsername,
                bio = it.bio,
                login = it.login,
                blog = it.blog,
                company = it.company,
                id =
                it.id,
                publicRepos = it.publicRepos,
                email = it.email,
                publicGists = it.publicGists,
                followers = it.followers,
                avatar = it.avatarUrl,
                htmlUrl = it.htmlUrl,
                following = it.following,
                name = it.name,
                location = it.location
            )
        }

    fun mapEntityToDomain(favoriteEntity: List<FavoriteEntity>): List<User> {
        val list = ArrayList<User>()
        favoriteEntity.map {
            val user = User(
                avatar = it.avatar,
                username = it.username
            )
            list.add(user)
        }
        return list
    }

    fun mapDomainToEntity(user: User): FavoriteEntity {
        return FavoriteEntity(
            avatar = user.avatar,
            username = user.username
        )
    }
}