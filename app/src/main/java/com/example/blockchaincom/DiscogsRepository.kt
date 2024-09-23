package com.example.blockchaincom

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

data class PaginationResponse(
    val pagination: Pagination,
    val releases: List<ReleaseApiModel>
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

data class ReleaseApiModel(
    val id: Int,
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

@Entity
data class ReleaseModel(
    @PrimaryKey val id: Int,
    val artistId: Int,
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

class ReleaseMapper @Inject constructor() {
    fun mapApiModelsToModels(apiModels: List<ReleaseApiModel>, artistId: Int): List<ReleaseModel> {
        return apiModels.map { apiModel ->
            ReleaseModel(
                id = apiModel.id,
                artistId = artistId,
                title = apiModel.title,
                type = apiModel.type,
                main_release = apiModel.main_release,
                artist = apiModel.artist,
                role = apiModel.role,
                resource_url = apiModel.resource_url,
                year = apiModel.year,
                thumb = apiModel.thumb,
                status = apiModel.status,
                format = apiModel.format,
                label = apiModel.label
            )
        }
    }
}

interface DiscogsApi {
    @GET("artists/{artist_id}/releases")
    suspend fun getArtistReleases(@Path("artist_id") artistId: Int): PaginationResponse
}

sealed class ReleaseResult {
    data class Success(val releases: List<ReleaseModel>) : ReleaseResult()
    data class Error(val message: String, val releases: List<ReleaseModel>) : ReleaseResult()
}

class ReleaseRepository @Inject constructor(
    private val discogsApi: DiscogsApi,
    private val releaseDao: ReleaseDao,
    private val releaseMapper: ReleaseMapper,
    @ApplicationContext private val context: Context
) {

    suspend fun getArtistReleases(artistId: Int): ReleaseResult {
        try {
            val response = discogsApi.getArtistReleases(artistId)
            val releases = releaseMapper.mapApiModelsToModels(
                artistId = artistId,
                apiModels = response.releases
            )
            releaseDao.insertReleases(releases)
            return ReleaseResult.Success(releases)
        } catch (e: Exception) {
            val releases = releaseDao.getReleasesByArtistId(artistId).firstOrNull() ?: emptyList()
            val message = context.getString(R.string.error_fetching_releases)
            return ReleaseResult.Error(message, releases)
        }
    }
}

