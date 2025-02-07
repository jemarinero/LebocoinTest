package com.lebocoin.data.repository

import com.lebocoin.data.datasource.InformationLocalDS
import com.lebocoin.data.datasource.InformationRemoteDS
import com.lebocoin.data.model.toDomain
import com.lebocoin.data.model.toEntity
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
    private val localDS: InformationLocalDS
): InformationRepository {

    override suspend fun getRemoteInformation(): ResultOf<Unit> {
        val result = remoteDS.getInformation()
        var response: ResultOf<Unit> = ResultOf.Failure(RequestFailure.UnknownError)
        result.doIfSuccess { data ->
            localDS.deleteAll()
            data.forEach { localDS.insertOrUpdate(it.toEntity()) }
           response = ResultOf.Success(Unit)
        }
        result.doIfFailure {
            response = ResultOf.Failure(it ?: RequestFailure.UnknownError)
        }
        return response
    }

    override fun getInformation(limit: Int, offset: Int): List<Information> =
        localDS.getAll(limit, offset).map { it.toDomain() }
}