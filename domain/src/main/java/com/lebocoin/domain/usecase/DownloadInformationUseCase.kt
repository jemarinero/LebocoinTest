package com.lebocoin.domain.usecase

import com.lebocoin.domain.common.BaseUseCase
import com.lebocoin.domain.dispatcher.DispatcherProvider
import com.lebocoin.domain.model.ResultOf
import com.lebocoin.domain.repository.InformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DownloadInformationUseCase
@Inject
constructor(
    private val informationRepository: InformationRepository,
    dispatcherProvider: DispatcherProvider
): BaseUseCase<Unit, ResultOf<Unit>>(dispatcherProvider){

    override fun configure(param: Unit): Flow<ResultOf<Unit>> = flow {
        emit(informationRepository.getRemoteInformation())
    }
}