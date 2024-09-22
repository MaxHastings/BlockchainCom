package com.example.blockchaincom

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
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

@Entity
data class Release(
    @PrimaryKey val id: Int,
    val title: String,
    val type: String,
    val main_release: Int?,
    val artist: String,
    val role: String,
    val resource_url: String,
    val year: Int?,
    val thumb: String?,
    val status: String?,    // for releases that contain the 'status' field
    val format: String?,    // for releases that contain the 'format' field
    val label: String?      // for releases that contain the 'label' field
)

interface DiscogsApi {
    @GET("artists/{artist_id}/releases")
    suspend fun getArtistReleases(@Path("artist_id") artistId: Int): PaginationResponse
}

sealed class ReleaseResult {
    data class Success(val releases: List<Release>, val message: String? = null) : ReleaseResult()
    data class Error(val message: String) : ReleaseResult()
}

class ReleaseRepository @Inject constructor(
    private val discogsApi: DiscogsApi,
    private val releaseDao: ReleaseDao,
) {

    suspend fun getArtistReleases(artistId: Int): ReleaseResult {
        try {
            val response = discogsApi.getArtistReleases(artistId)
            releaseDao.insertReleases(response.releases)
            return ReleaseResult.Success(response.releases)
        } catch (e: Exception) {
            val releases = releaseDao.getReleasesByArtistId(artistId).firstOrNull()
            return if (releases != null) {
                ReleaseResult.Success(releases, "Local data fetched")
            } else {
                ReleaseResult.Error(e.message ?: "No Artists Found")
            }
        }
    }
}

