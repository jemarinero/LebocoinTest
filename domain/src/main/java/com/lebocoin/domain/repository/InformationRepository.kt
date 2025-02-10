package com.lebocoin.domain.repository

import com.lebocoin.domain.model.Information
import com.lebocoin.domain.model.ResultOf

interface InformationRepository {
    suspend fun getRemoteInformation(): ResultOf<Unit>
    fun getInformation(limit: Int, offset: Int): ResultOf<List<Information>>
}