package com.lebocoin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lebocoin.data.database.dao.InformationDao
import com.lebocoin.data.database.entity.InformationEntity

@Database(entities = [InformationEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun informationDao(): InformationDao

}