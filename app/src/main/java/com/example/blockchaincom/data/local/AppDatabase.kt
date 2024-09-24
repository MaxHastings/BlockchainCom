package com.example.blockchaincom.data.local

import androidx.room.Database
import com.example.blockchaincom.data.local.releases.Release
import com.example.blockchaincom.data.local.releases.ReleaseDao

@Database(entities = [Release::class], version = 1)
abstract class AppDatabase : androidx.room.RoomDatabase() {
    abstract fun releaseDao(): ReleaseDao
}