package com.example.blockchaincom

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    abstract fun bindDiscogsApi(
        discogsApiImpl: DiscogsApiImpl
    ): DiscogsApi

}