package com.example.blockchaincom.data.remote.releases

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of the [ReleasesApi] interface that uses OkHttp to make network requests
 * and Gson to parse JSON responses.
 *
 * @param httpClient The OkHttp client used for making network requests.
 * @param gson The Gson instance used for parsing JSON responses.
 */
class ReleasesApi @Inject constructor(
    private val httpClient: OkHttpClient,
    private val gson: Gson,
    private val dispatcher: CoroutineDispatcher
) {

    /**
     * Retrieves a list of releases for a given artist ID from the Discogs API.
     *
     * @param artistId The ID of the artist.
     * @return A [PaginationResponse] object containing the list of releases and pagination information.
     * @throws IOException If there is an error making the network request or parsing the response.
     */
    suspend fun getArtistReleases(artistId: Int): PaginationResponse {
        val request = Request.Builder()
            .url("https://api.discogs.com/artists/$artistId/releases")
            .build()

        return withContext(dispatcher) {
            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body()?.string()
                gson.fromJson(responseBody, PaginationResponse::class.java)
            } else {
                throw IOException("Unexpected response code: ${response.code()}")
            }
        }
    }
}