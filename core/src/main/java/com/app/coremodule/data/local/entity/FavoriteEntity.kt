package com.app.coremodule.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @ColumnInfo(name = "bio")
    val bio: String? = null,

    @PrimaryKey
    @ColumnInfo(name = "username")
    val login: String,

    @ColumnInfo(name = "blog")
    val blog: String? = null,

    @ColumnInfo(name = "company")
    val company: String? = null,

    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "repository")
    val publicRepos: Int = 0,

    @ColumnInfo(name = "gists")
    val publicGists: Int = 0,

    @ColumnInfo(name = "followers")
    val followers: Int = 0,

    @ColumnInfo(name = "avatar")
    val avatar: String? = null,

    @ColumnInfo(name = "html")
    val htmlUrl: String? = null,

    @ColumnInfo(name = "following")
    val following: Int = 0,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "location")
    val location: String? = null
)