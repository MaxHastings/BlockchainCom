package com.example.blockchaincom.data.remote.releases

import retrofit2.http.GET
import retrofit2.http.Path

interface ReleasesApi {
    @GET("artists/{artist_id}/releases")
    suspend fun getArtistReleases(@Path("artist_id") artistId: Int): PaginationResponse
}