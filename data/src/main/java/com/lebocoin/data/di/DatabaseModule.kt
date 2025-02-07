package com.lebocoin.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.lebocoin.data.database.AppDatabase
import com.lebocoin.data.database.dao.InformationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesContext(context: Application): Context {
        return context.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemDao(appDatabase: AppDatabase): InformationDao = appDatabase.informationDao()

}