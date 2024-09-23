package com.example.blockchaincom.data.local.releases

import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface ReleaseDao {

    @androidx.room.Query("SELECT * FROM `Release` WHERE artistId = :artistId")
    fun getReleasesByArtistId(artistId: Int): Flow<List<Release>>

    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertReleases(releases: List<Release>)
}