package com.app.coremodule.data.local.room

import android.database.Cursor
import androidx.room.*
import com.app.coremodule.data.local.entity.FavoriteEntity
import com.app.coremodule.data.local.entity.RecentEntity
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

    @Query("DELETE FROM favorite WHERE username = :username")
    fun deleteFavoriteByUsername(username: String)

    @Query("SELECT * FROM recent ORDER BY created_at DESC")
    fun getAllRecent(): Flow<List<RecentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecent(recentEntity: RecentEntity)

    @Query("DELETE FROM recent where created_at NOT IN (SELECT created_at from recent ORDER BY created_at DESC LIMIT 5)")
    fun deleteRecent()

    @Query("DELETE FROM recent WHERE username = :username")
    fun deleteRecent(username: String)

    @Query("SELECT * FROM favorite")
    fun getAllFavoriteCursor(): Cursor
}