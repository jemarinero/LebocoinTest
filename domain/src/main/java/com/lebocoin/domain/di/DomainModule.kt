package com.lebocoin.domain.di

import com.lebocoin.domain.dispatcher.DefaultDispatcherProvider
import com.lebocoin.domain.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindDispatcherProvider(imp: DefaultDispatcherProvider): DispatcherProvider
}