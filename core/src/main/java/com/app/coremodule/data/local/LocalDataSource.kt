package com.app.coremodule.data.local

import com.app.coremodule.data.local.model.FavoriteEntity
import com.app.coremodule.data.local.room.AppDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: AppDao) {
    fun getAllFavorite(): Flow<List<FavoriteEntity>> = dao.getAllFavorite()
    fun insertFavorite(favoriteEntity: FavoriteEntity) = dao.insertFavorite(favoriteEntity)
    fun deleteFavorite(favoriteEntity: FavoriteEntity) = dao.deleteFavorite(favoriteEntity)
}