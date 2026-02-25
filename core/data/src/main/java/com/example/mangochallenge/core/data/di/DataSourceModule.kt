package com.example.mangochallenge.core.data.di

import com.example.mangochallenge.core.data.local.datasource.LocalDataSource
import com.example.mangochallenge.core.data.local.datasource.LocalDataSourceImpl
import com.example.mangochallenge.core.data.remote.datasource.RemoteDataSource
import com.example.mangochallenge.core.data.remote.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}
