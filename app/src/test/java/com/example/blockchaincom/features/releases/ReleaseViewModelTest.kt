package com.example.blockchaincom.features.releases

import com.example.blockchaincom.data.local.releases.Release
import com.example.blockchaincom.domain.usecases.GetArtistReleasesUseCase
import com.example.blockchaincom.features.releases.data.ReleaseResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

class ReleaseViewModelTest {

    private lateinit var viewModel: ReleaseViewModel
    private lateinit var getArtistReleasesUseCase: GetArtistReleasesUseCase
    private val testDispatcher = UnconfinedTestDispatcher() // Add a test dispatcher

    @Before
    fun setup() {
        getArtistReleasesUseCase = mockk()
        viewModel = ReleaseViewModel(getArtistReleasesUseCase, testDispatcher) // Pass dispatcher to ViewModel
    }

    @Test
    fun `getArtistReleases should update state to Success when use case succeeds`(): Unit = runBlocking {
        val artistId = 123
        val releases = listOf(
            Release(
                id = 1, artistId = artistId, title = "Release 1", year = 2022,
                status = "Accepted", format = "Album", type = "release", main_release = null,
                artist = "Artist Name", role = "Main", resource_url = "release_url",
                thumb = "thumb_url", label = "Label"
            )
        )
        coEvery { getArtistReleasesUseCase(artistId) } returns ReleaseResult.Success(releases)

        viewModel.getArtistReleases(artistId)

        viewModel.uiState.value shouldBe ReleaseUiState.Success(releases)
    }

    @Test
    fun `getArtistReleases should update state to Error when use case fails`(): Unit = runBlocking {
        val artistId = 123
        val errorMessage = "Network error"
        coEvery { getArtistReleasesUseCase(artistId) } returns ReleaseResult.Error(
            errorMessage,
            emptyList()
        )

        viewModel.getArtistReleases(artistId)

        viewModel.uiState.value shouldBe ReleaseUiState.Error(emptyList(), errorMessage)
    }
}