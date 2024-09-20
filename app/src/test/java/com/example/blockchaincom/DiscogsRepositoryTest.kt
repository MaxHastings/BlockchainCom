package com.example.blockchaincom

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DiscogsRepositoryTest {

    @Mock
    private lateinit var discogsApi: DiscogsApi

    private lateinit var repository: DiscogsRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = DiscogsRepository(discogsApi) // Inject the mocked API
    }

    @Test
    fun `fetchArtistReleases should return expected data`() = runBlocking {
        // Mock the API response
        val artistId = 1
        val mockResponse = ArtistReleasesResponse(
            listOf(
                Release("Album 1", 1970, "Main"),
                Release("Album 2", 1972, "Main")
            )
        )
        Mockito.`when`(discogsApi.getArtistReleases(artistId)).thenReturn(mockResponse)

        // Call the function and get the result
        val result = repository.fetchArtistReleases(artistId)

        // Assert that the result matches the expected data
        assertEquals(mockResponse, result)
    }
}