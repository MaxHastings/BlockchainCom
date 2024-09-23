package com.example.blockchaincom.features.releases.data

import android.content.Context
import com.example.blockchaincom.data.remote.releases.ReleasesApi
import com.example.blockchaincom.R
import com.example.blockchaincom.data.local.releases.ReleaseDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ReleaseRepositoryImpl @Inject constructor(
    private val releasesApi: ReleasesApi,
    private val releaseDao: ReleaseDao,
    private val releaseMapper: ReleaseMapper,
    @ApplicationContext private val context: Context
) {

    suspend fun getArtistReleases(artistId: Int): ReleaseResult {
        try {
            val response = releasesApi.getArtistReleases(artistId)
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

