package com.example.blockchaincom

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

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

