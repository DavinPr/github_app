package com.app.coremodule.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @ColumnInfo(name = "follower")
    val follower: Int? = null,

    @ColumnInfo(name = "following")
    val following: Int? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "company")
    val company: String? = null,

    @ColumnInfo(name = "location")
    val location: String? = null,

    @ColumnInfo(name = "avatar")
    val avatar: String? = null,

    @ColumnInfo(name = "repository")
    val repository: Int? = null,

    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String
)