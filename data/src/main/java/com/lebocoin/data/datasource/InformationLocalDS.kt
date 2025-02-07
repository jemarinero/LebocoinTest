package com.lebocoin.data.datasource

import com.lebocoin.data.database.dao.InformationDao
import com.lebocoin.data.database.entity.InformationEntity
import javax.inject.Inject

class InformationLocalDS
@Inject
constructor(private val itemDao: InformationDao) {

    suspend fun insertOrUpdate(item: InformationEntity) = itemDao.insertOrUpdate(item)
    fun getAll(limit: Int, offset: Int) = itemDao.getAll(limit, offset)
    fun deleteAll() = itemDao.deleteAll()
}