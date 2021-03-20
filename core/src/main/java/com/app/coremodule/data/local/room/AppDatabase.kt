package com.app.coremodule.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.coremodule.data.local.entity.FavoriteEntity
import com.app.coremodule.data.local.entity.RecentEntity

@Database(
    entities = [FavoriteEntity::class, RecentEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}