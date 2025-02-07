package com.lebocoin.domain.usecase

import com.lebocoin.domain.common.BaseUseCase
import com.lebocoin.domain.dispatcher.DispatcherProvider
import com.lebocoin.domain.model.Information
import com.lebocoin.domain.model.ResultOf
import com.lebocoin.domain.repository.InformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInformationUseCase
@Inject
constructor(
    private val informationRepository: InformationRepository,
    dispatcherProvider: DispatcherProvider
): BaseUseCase<Unit, ResultOf<List<Information>>>(dispatcherProvider){

    override fun configure(param: Unit): Flow<ResultOf<List<Information>>> = flow {
        emit(informationRepository.getInformation())
    }
}