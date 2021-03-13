package com.app.coremodule.data.local

import com.app.coremodule.data.local.model.FavoriteEntity
import com.app.coremodule.data.local.room.AppDao
import com.app.coremodule.data.local.sharedpref.ISharedPref
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: AppDao, private val sharedPref: ISharedPref) {
    fun getAllFavorite(): Flow<List<FavoriteEntity>> = dao.getAllFavorite()
    fun insertFavorite(favoriteEntity: FavoriteEntity) = dao.insertFavorite(favoriteEntity)
    fun deleteFavorite(favoriteEntity: FavoriteEntity) = dao.deleteFavorite(favoriteEntity)

    fun putFragmentTag(tag : String) = sharedPref.putFragmentTag(tag)
    fun getFragmentTag() : String? = sharedPref.getFragmentTag()
}