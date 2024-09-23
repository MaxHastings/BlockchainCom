package com.example.blockchaincom.data.remote.releases

interface ReleasesApi {
    suspend fun getArtistReleases(artistId: Int): PaginationResponse
}