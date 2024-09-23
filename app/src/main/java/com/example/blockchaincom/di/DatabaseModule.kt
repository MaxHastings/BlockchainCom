package com.example.blockchaincom.di

import android.content.Context
import androidx.room.Room
import com.example.blockchaincom.data.local.AppDatabase
import com.example.blockchaincom.data.local.releases.ReleaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "discogs_database"
        ).build()
    }

    @Provides
    fun provideReleaseDao(appDatabase: AppDatabase): ReleaseDao {
        return appDatabase.releaseDao()
    }
}