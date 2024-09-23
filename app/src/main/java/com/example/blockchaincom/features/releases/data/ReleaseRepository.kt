package com.example.blockchaincom.features.releases.data

import android.content.Context
import com.example.blockchaincom.R
import com.example.blockchaincom.data.local.releases.ReleaseDao
import com.example.blockchaincom.data.remote.releases.ReleasesApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

/**
 * Repository implementation class that handles fetching and managing artist releases from Discogs API
 * and local database. This class interacts with Discogs API to get artist releases and saves the fetched
 * data locally in a database.
 *
 * @property releasesApi The API service for fetching artist releases from Discogs.
 * @property releaseDao The DAO for accessing and managing the release data in the local database.
 * @property releaseMapper The mapper used for converting API response models to local domain models.
 * @property context Application context for retrieving resources (like error messages).
 *
 */
class ReleaseRepository @Inject constructor(
    private val releasesApi: ReleasesApi,
    private val releaseDao: ReleaseDao,
    private val releaseMapper: ReleaseMapper,
    @ApplicationContext private val context: Context
) {

    /**
     * Fetches releases for the given artist from Discogs API. If successful, the releases are stored
     * in the local database. In case of an error (e.g., network issue), it attempts to retrieve any
     * cached releases from the local database. The method returns a [ReleaseResult] that indicates
     * either success with a list of releases or an error with an appropriate message and cached data.
     *
     * @param artistId The ID of the artist whose releases are to be fetched.
     *
     * @return [ReleaseResult] A sealed class that either returns a Success with a list of releases or
     * an Error with a message and cached releases if available.
     */
    suspend fun getArtistReleases(artistId: Int): ReleaseResult {
        try {
            // Fetch artist releases from Discogs API
            val response = releasesApi.getArtistReleases(artistId)

            // Map the API models to the domain models
            val releases = releaseMapper.mapApiModelsToModels(
                artistId = artistId,
                apiModels = response.releases
            )

            // Insert the releases into the local database
            releaseDao.insertReleases(releases)

            // Return success with the list of releases
            return ReleaseResult.Success(releases)
        } catch (e: Exception) {
            // Fetch cached releases from the local database
            val releases = releaseDao.getReleasesByArtistId(artistId).firstOrNull() ?: emptyList()

            val message = context.getString(R.string.error_fetching_releases)

            // Return error with message and cached releases
            return ReleaseResult.Error(message, releases)
        }
    }
}


