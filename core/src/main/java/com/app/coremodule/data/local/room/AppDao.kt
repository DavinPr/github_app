package com.app.coremodule.data.local.room

import androidx.room.*
import com.app.coremodule.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorite(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getDetailFavorite(username: String): Flow<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    fun deleteFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT EXISTS (SELECT * FROM favorite WHERE username = :username)")
    fun isFavorite(username: String): Flow<Boolean>
}