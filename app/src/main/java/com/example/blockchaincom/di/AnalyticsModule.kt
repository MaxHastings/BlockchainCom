package com.example.blockchaincom.di

import com.example.blockchaincom.data.remote.releases.ReleasesApi
import com.example.blockchaincom.data.remote.releases.ReleasesApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    abstract fun bindDiscogsApi(
        discogsApiImpl: ReleasesApiImpl
    ): ReleasesApi

}