package com.lebocoin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lebocoin.data.database.entity.InformationEntity

@Dao
interface InformationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: InformationEntity): Long

    @Query("SELECT * FROM information limit :limit offset :offset")
    fun getAll(limit: Int, offset: Int) : List<InformationEntity>

    @Query("DELETE FROM information")
    fun deleteAll()
}