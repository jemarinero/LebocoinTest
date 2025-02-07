package com.lebocoin.data.datasource

import com.lebocoin.data.common.FailureFactory
import com.lebocoin.data.common.safeCall
import com.lebocoin.data.model.InformationDTO
import com.lebocoin.data.service.ApiService
import com.lebocoin.domain.model.ResultOf
import javax.inject.Inject

class InformationRemoteDS
@Inject
constructor(
    private val apiService: ApiService
){

    suspend fun getInformation(): ResultOf<List<InformationDTO>> =
        try {
            apiService.getInformation().safeCall({ response ->
               response
            })
        } catch (ex: Exception) {
            FailureFactory().handleException(ex)
        }

}