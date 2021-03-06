package com.app.coremodule.domain.repository

import android.database.Cursor
import com.app.coremodule.data.Resource
import com.app.coremodule.domain.usecase.model.Detail
import com.app.coremodule.domain.usecase.model.Recent
import com.app.coremodule.domain.usecase.model.User
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    fun getSearchUser(username: String): Flow<Resource<List<User>>>
    fun getTopUser(): Flow<Resource<List<User>>>
    fun getDetailUser(username: String): Flow<Resource<Detail>>
    fun getUserFollowing(username: String): Flow<Resource<List<User>>>
    fun getUserFollowers(username: String): Flow<Resource<List<User>>>

    fun getAllFavorite(): Flow<Resource<List<User>>>
    fun insertFavorite(detail: Detail)
    fun deleteFavorite(detail: Detail)
    fun deleteFavoriteByUsername(username: String)

    fun getAllRecent(): Flow<List<Recent>>
    fun insertRecent(detail: Detail)
    fun deleteRecent(username: String)

    fun getLocale(): String?

    fun getAllFavoriteCursor(): Cursor
}