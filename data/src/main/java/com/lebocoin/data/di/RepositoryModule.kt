package com.lebocoin.data.di

import com.lebocoin.data.repository.InformationRepositoryImpl
import com.lebocoin.domain.repository.InformationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [RepositoryModule.RepositorySubModule::class])
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface RepositorySubModule {

        @Binds
        fun bindInformationRepository(impl: InformationRepositoryImpl): InformationRepository
    }
}