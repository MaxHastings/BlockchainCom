package com.example.blockchaincom


import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

class DiscogsApiImpl @Inject constructor(
    private val httpClient: OkHttpClient,
    private val gson: Gson
) : DiscogsApi {

    override suspend fun getArtistReleases(artistId: Int): PaginationResponse {
        val request = Request.Builder()
            .url("https://api.discogs.com/artists/$artistId/releases")
            .build()

        return withContext(Dispatchers.IO) {
            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body()?.string()
                gson.fromJson(responseBody, PaginationResponse::class.java)
            } else {
                // Handle error cases (e.g., throw an exception or return a default value)
                throw IOException("Unexpected response code: ${response.code()}")
            }
        }
    }
}