package com.lebocoin.data.repository

import com.lebocoin.data.common.IMAGE_BASE_URL
import com.lebocoin.data.common.IMAGE_OLD_URL
import com.lebocoin.data.datasource.InformationLocalDS
import com.lebocoin.data.datasource.InformationRemoteDS
import com.lebocoin.data.model.toDomain
import com.lebocoin.data.model.toEntity
import com.lebocoin.domain.model.Information
import com.lebocoin.domain.model.ErrorType
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
        var response: ResultOf<Unit> = ResultOf.Failure(ErrorType.UnknownError)
        result.doIfSuccess { data ->
            localDS.deleteAll()
            data.forEach { info ->
                localDS.insertOrUpdate(
                    info.copy(
                        url = info.url.replace(IMAGE_OLD_URL, IMAGE_BASE_URL),
                        thumbnailUrl = info.thumbnailUrl.replace(IMAGE_OLD_URL, IMAGE_BASE_URL)
                    ).toEntity())
            }
           response = ResultOf.Success(Unit)
        }
        result.doIfFailure {
            response = ResultOf.Failure(it ?: ErrorType.UnknownError)
        }
        return response
    }

    override fun getInformation(limit: Int, offset: Int): ResultOf<List<Information>> {
        val result = localDS.getAll(limit, offset)
        return if(result.isEmpty()){
            ResultOf.Failure(ErrorType.EmptyError)
        } else {
            ResultOf.Success(result.map { it.toDomain() })
        }
    }
}