package com.app.coremodule.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recent")
data class RecentEntity(
    @PrimaryKey
    @ColumnInfo(name = "username")
    val login: String,

    @ColumnInfo(name = "created_at")
    var created_at: Date? = null
)
