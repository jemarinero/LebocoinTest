package com.lebocoin.data.repository

import com.lebocoin.data.datasource.InformationRemoteDS
import com.lebocoin.data.model.toDomain
import com.lebocoin.domain.model.Information
import com.lebocoin.domain.model.RequestFailure
import com.lebocoin.domain.model.ResultOf
import com.lebocoin.domain.model.doIfFailure
import com.lebocoin.domain.model.doIfSuccess
import com.lebocoin.domain.repository.InformationRepository
import javax.inject.Inject

class InformationRepositoryImpl
@Inject
constructor(
    private val remoteDS: InformationRemoteDS,
    //private val localDS: InformationLocalDS
): InformationRepository {

    override suspend fun getInformation(): ResultOf<List<Information>> {
        val result = remoteDS.getInformation()
        var response: ResultOf<List<Information>> = ResultOf.Failure(RequestFailure.UnknownError)
        result.doIfSuccess { data ->
           response = ResultOf.Success(data.map { it.toDomain() })
        }
        result.doIfFailure {
            response = ResultOf.Failure(it ?: RequestFailure.UnknownError)
        }
        return response
    }
}