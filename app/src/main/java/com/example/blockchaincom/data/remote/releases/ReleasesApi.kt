package com.example.blockchaincom.data.remote.releases

import com.example.blockchaincom.data.remote.ApiError
import com.example.blockchaincom.data.remote.ApiResult
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

/**
 * Implementation of the [ReleasesApi] interface that uses OkHttp to make network requests
 * and Gson to parse JSON responses.
 *
 * @param httpClient The OkHttp client used for making network requests.
 * @param gson The Gson instance used for parsing JSON responses.
 * @param dispatcher The CoroutineDispatcher used for performing network operations.
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
     * @return An [ApiResult] object wrapping either a [PaginationResponse] on success or an error message on failure.
     * @throws Exception If there is an error making the network request or parsing the response.
     */
    suspend fun getArtistReleases(artistId: Int): ApiResult<PaginationResponse> {
        val request = Request.Builder()
            .url("https://api.discogs.com/artists/$artistId/releases")
            .build()
        return withContext(dispatcher) {
            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body()?.string()
                ApiResult.Success(gson.fromJson(responseBody, PaginationResponse::class.java))
            } else {
                val responseBody = response.body()?.string()
                val error = gson.fromJson(responseBody, ApiError::class.java)
                ApiResult.Error(error.message)
            }
        }
    }
}