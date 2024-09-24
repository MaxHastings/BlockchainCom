package com.example.blockchaincom.features.releases.data

import android.content.Context
import androidx.compose.ui.geometry.isEmpty
import com.example.blockchaincom.data.Urls
import com.example.blockchaincom.data.local.releases.Release
import com.example.blockchaincom.data.local.releases.ReleaseDao
import com.example.blockchaincom.data.remote.ApiResult
import com.example.blockchaincom.data.remote.releases.Pagination
import com.example.blockchaincom.data.remote.releases.PaginationResponse
import com.example.blockchaincom.data.remote.releases.ReleaseApiModel
import com.example.blockchaincom.data.remote.releases.ReleasesApi
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ReleaseRepositoryTest {

    private lateinit var releaseRepository: ReleaseRepository
    private lateinit var releasesApi: ReleasesApi
    private lateinit var releasesDao: ReleaseDao
    private lateinit var releasesMapper: ReleaseMapper
    private lateinit var context: Context

    @Before
    fun setup() {
        releasesApi = mockk()
        releasesDao = mockk()
        releasesMapper = mockk()
        context = mockk()

        releaseRepository =
            ReleaseRepository(
                releaseDao = releasesDao,
                releasesApi = releasesApi,
                releaseMapper = releasesMapper,
                context = context
            )
    }

    @Test
    fun `getReleasesForArtist should return releases from API when successful`() = runBlocking {
        val artistId = 123
        val releases = listOf(
            Release(
                id = 1,
                artistId = artistId,
                title = "Release 1",
                year = 2022,
                status = "Accepted",
                format = "Album",
                type = "release",
                mainRelease = null,
                artist = "Artist Name",
                role = "Main",
                resourceUrl = "https://api.discogs.com/releases/1",
                thumb = "https://example.com/thumb1.jpg",
                label = "Label A"
            ),
            Release(
                id = 2,
                artistId = artistId,
                title = "Release 2",
                year = 2023,
                status = "Accepted",
                format = "Single",
                type = "release",
                mainRelease = null,
                artist = "Artist Name",
                role = "Main",
                resourceUrl = "https://api.discogs.com/releases/2",
                thumb = "https://example.com/thumb2.jpg",
                label = "Label B"
            )
        )

        val apiReleases = PaginationResponse(
            pagination = Pagination(
                page = 1,
                pages = 1,
                perPage = 50,
                items = 2,
                urls = Urls(
                    last = "https://api.discogs.com/artists/$artistId/releases?page=1&per_page=50",
                    next = null
                )
            ),
            releases = listOf(
                ReleaseApiModel(
                    id = 1,
                    title = "Release 1",
                    year = 2022,
                    status = "Accepted",
                    format = "Album",
                    type = "release",
                    mainRelease = null,
                    artist = "Artist Name",
                    role = "Main",
                    resourceUrl = "https://api.discogs.com/releases/1",
                    thumb = "https://example.com/thumb1.jpg",
                    label = "Label A"
                ),
                ReleaseApiModel(
                    id = 2,
                    title = "Release 2",
                    year = 2023,
                    status = "Accepted",
                    format = "Single",
                    type = "release",
                    mainRelease = null,
                    artist = "Artist Name",
                    role = "Main",
                    resourceUrl = "https://api.discogs.com/releases/2",
                    thumb = "https://example.com/thumb2.jpg",
                    label = "Label B"
                )
            )
        )
        coEvery { releasesApi.getArtistReleases(artistId) } returns ApiResult.Success(apiReleases)
        coEvery { releasesMapper.mapApiModelsToModels(apiReleases.releases, artistId) } returns releases
        coEvery { context.getString(any()) } returns "Error fetching releases"
        coEvery { releasesDao.insertReleases(releases) } just Runs

        val result = releaseRepository.getArtistReleases(artistId)
        // Verify that the releases are fetched from the API and inserted into the DAO
        coVerify { releasesDao.insertReleases(releases) }
        // Assert that the result is a success with the expected releases
        Assert.assertTrue(result is ReleaseResult.Success)
        Assert.assertEquals(releases, (result as ReleaseResult.Success).releases)
    }

    @Test
    fun `getArtistReleases should return Error when exception is thrown`() = runBlocking {
        val artistId = 123
        val exception = IOException("Network error")

        coEvery { releasesDao.getReleasesByArtistId(artistId) } returns flowOf(emptyList())
        coEvery { releasesApi.getArtistReleases(artistId) } throws exception
        coEvery { context.getString(any()) } returns "Error fetching releases"

        val result = releaseRepository.getArtistReleases(artistId)

        // Assert that the result is ReleaseResult.Error
        Assert.assertTrue(result is ReleaseResult.Error)
    }

    @Test
    fun `getArtistReleases should return Error when ApiResult is Error`() = runTest {

        val errorMessage = "No Artist Found"
        coEvery { releasesApi.getArtistReleases(any()) } returns ApiResult.Error(errorMessage)

        val result = releaseRepository.getArtistReleases(123)

        // Assert that the result is ReleaseResult.Error
        Assert.assertTrue(result is ReleaseResult.Error)

        // Assert that the error message is correct
        Assert.assertEquals(errorMessage, (result as ReleaseResult.Error).message)

        // Assert that the list of releases is empty
        Assert.assertTrue(result.releases.isEmpty())
    }
}