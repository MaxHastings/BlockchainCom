package com.example.blockchaincom

import androidx.room.Database

@Database(entities = [Release::class], version = 1)
abstract class AppDatabase : androidx.room.RoomDatabase() {
    abstract fun releaseDao(): ReleaseDao
}