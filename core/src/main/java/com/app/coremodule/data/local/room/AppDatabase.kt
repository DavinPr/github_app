package com.app.coremodule.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.coremodule.data.local.model.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao() : AppDao
}