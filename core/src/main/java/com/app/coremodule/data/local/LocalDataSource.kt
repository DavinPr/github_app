package com.app.coremodule.data.local

import android.database.Cursor
import com.app.coremodule.data.local.entity.FavoriteEntity
import com.app.coremodule.data.local.entity.RecentEntity
import com.app.coremodule.data.local.room.AppDao
import com.app.coremodule.data.local.sharedpref.ISharedPref
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: AppDao, private val sharedPref: ISharedPref) {
    fun getAllFavorite(): Flow<List<FavoriteEntity>> = dao.getAllFavorite()
    fun getDetailFavorite(username: String): Flow<FavoriteEntity> = dao.getDetailFavorite(username)
    fun insertFavorite(favoriteEntity: FavoriteEntity) = dao.insertFavorite(favoriteEntity)
    fun deleteFavorite(favoriteEntity: FavoriteEntity) = dao.deleteFavorite(favoriteEntity)
    fun deleteFavoriteByUsername(username: String) = dao.deleteFavoriteByUsername(username)

    fun getAllRecent(): Flow<List<RecentEntity>> = dao.getAllRecent()
    fun insertRecent(recentEntity: RecentEntity) = dao.insertRecent(recentEntity)
    fun deleteRecent() = dao.deleteRecent()
    fun deleteRecentByUsername(username: String) = dao.deleteRecent(username)

    fun getLocale() = sharedPref.getLocale()

    fun getAllFavoriteCursor(): Cursor = dao.getAllFavoriteCursor()
}