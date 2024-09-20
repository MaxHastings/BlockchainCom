package com.example.blockchaincom

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//
//    @Provides
//    @Singleton
//    fun provideDiscogsApi(retrofit: Retrofit): DiscogsApi {
//        return retrofit.create(DiscogsApi::class.java)
//    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.discogs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}