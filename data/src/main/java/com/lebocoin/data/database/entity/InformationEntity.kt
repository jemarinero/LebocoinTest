package com.lebocoin.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "information", primaryKeys = ["albumId", "id"])
data class InformationEntity(
    @ColumnInfo(name = "albumId") val albumId: Int,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String
)