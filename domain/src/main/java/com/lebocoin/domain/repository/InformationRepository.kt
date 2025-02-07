package com.lebocoin.domain.repository

import com.lebocoin.domain.model.Information
import com.lebocoin.domain.model.ResultOf

interface InformationRepository {
    suspend fun getInformation(): ResultOf<List<Information>>
}