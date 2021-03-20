package com.app.coremodule.utils

import com.app.coremodule.data.local.entity.FavoriteEntity
import com.app.coremodule.data.local.entity.RecentEntity
import com.app.coremodule.data.remote.response.DetailResponse
import com.app.coremodule.data.remote.response.UserItem
import com.app.coremodule.domain.usecase.model.Detail
import com.app.coremodule.domain.usecase.model.User
import java.util.*
import kotlin.collections.ArrayList

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
                bio = it.bio,
                login = it.login,
                blog = it.blog,
                company = it.company,
                id =
                it.id,
                publicRepos = it.publicRepos,
                publicGists = it.publicGists,
                followers = it.followers,
                avatar = it.avatarUrl,
                htmlUrl = it.htmlUrl,
                following = it.following,
                name = it.name,
                location = it.location,
                isFavorite = false
            )
        }

    fun mapFavoriteListEntityToDomain(favoriteEntity: List<FavoriteEntity>): List<User> {
        val list = ArrayList<User>()
        favoriteEntity.map {
            val user = User(
                avatar = it.avatar,
                username = it.login,
                name = it.name
            )
            list.add(user)
        }
        return list
    }

    fun mapDetailEntityToDomain(entity: FavoriteEntity?): Detail {
        return if (entity != null) {
            Detail(
                bio = entity.bio,
                login = entity.login,
                blog = entity.blog,
                company = entity.company,
                id = entity.id,
                publicRepos = entity.publicRepos,
                publicGists = entity.publicGists,
                followers = entity.followers,
                avatar = entity.avatar,
                htmlUrl = entity.htmlUrl,
                following = entity.following,
                name = entity.name,
                location = entity.location,
                isFavorite = true
            )
        } else {
            Detail()
        }
    }

    fun mapDomainToFavoriteEntity(detail: Detail): FavoriteEntity {
        return FavoriteEntity(
            bio = detail.bio,
            login = detail.login ?: "",
            blog = detail.blog,
            company = detail.company,
            id = detail.id,
            publicRepos = detail.publicRepos,
            publicGists = detail.publicGists,
            followers = detail.followers,
            avatar = detail.avatar,
            htmlUrl = detail.htmlUrl,
            following = detail.following,
            name = detail.name,
            location = detail.location
        )
    }

    fun mapRecentEntityToDomain(recentEntity: List<RecentEntity>): List<User> {
        val list = ArrayList<User>()
        recentEntity.map {
            val user = User(
                avatar = it.avatar,
                username = it.login,
                name = it.name
            )
            list.add(user)
        }
        return list
    }

    fun mapDomainToRecentEntity(detail: Detail): RecentEntity {
        return RecentEntity(
            login = detail.login ?: "",
            name = detail.name,
            avatar = detail.avatar,
            created_at = Calendar.getInstance().time
        )
    }
}