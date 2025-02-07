package com.lebocoin.domain.usecase

import com.lebocoin.domain.common.BaseUseCase
import com.lebocoin.domain.dispatcher.DispatcherProvider
import com.lebocoin.domain.model.Information
import com.lebocoin.domain.model.ResultOf
import com.lebocoin.domain.repository.InformationRepository
import com.lebocoin.domain.usecase.GetInformationUseCase.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInformationUseCase
@Inject
constructor(
    private val informationRepository: InformationRepository,
    dispatcherProvider: DispatcherProvider
): BaseUseCase<Pagination, ResultOf<List<Information>>>(dispatcherProvider){

    override fun configure(param: Pagination): Flow<ResultOf<List<Information>>> = flow {
        emit(ResultOf.Success(informationRepository.getInformation(param.limit, param.offset)))
    }

    data class Pagination(
        val limit: Int,
        val offset: Int
    )
}