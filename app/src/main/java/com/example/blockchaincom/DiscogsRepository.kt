package com.example.blockchaincom

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

data class PaginationResponse(
    val pagination: Pagination,
    val releases: List<Release>
)

data class Pagination(
    val page: Int,
    val pages: Int,
    val per_page: Int,
    val items: Int,
    val urls: Urls
)

data class Urls(
    val last: String?,
    val next: String?
)

data class Release(
    val id: Int,
    val title: String,
    val type: String,
    val main_release: Int?,
    val artist: String,
    val role: String,
    val resource_url: String,
    val year: Int?,
    val thumb: String?,
    val stats: Stats?,
    val status: String?,    // for releases that contain the 'status' field
    val format: String?,    // for releases that contain the 'format' field
    val label: String?      // for releases that contain the 'label' field
)

data class Stats(
    val community: Community
)

data class Community(
    val in_wantlist: Int,
    val in_collection: Int
)


// Define the Retrofit interface for the Discogs API
interface DiscogsApi {
    @GET("artists/{artist_id}/releases")
    suspend fun getArtistReleases(@Path("artist_id") artistId: Int): PaginationResponse
}

// Create the data layer class
class DiscogsRepository @Inject constructor(private val discogsApi: DiscogsApi) {

    suspend fun fetchArtistReleases(artistId: Int): PaginationResponse {
        return withContext(Dispatchers.IO) {
            discogsApi.getArtistReleases(artistId)
        }
    }
}