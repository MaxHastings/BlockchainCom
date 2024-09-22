package com.example.blockchaincom

import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface ReleaseDao {

    @androidx.room.Query("SELECT * FROM `RELEASE` WHERE id = :artistId")
    fun getReleasesByArtistId(artistId: Int): Flow<List<Release>>

    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertReleases(releases: List<Release>)
}