package com.example.blockchaincom

import retrofit2.http.GET
import retrofit2.http.Path

interface DiscogsApi {
    @GET("artists/{artist_id}/releases")
    suspend fun getArtistReleases(@Path("artist_id") artistId: Int): PaginationResponse
}