package com.example.blockchaincom.data.remote

import com.example.blockchaincom.data.remote.releases.ReleasesApi
import com.google.gson.Gson
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class ReleasesApiTest {

    private lateinit var releasesApi: ReleasesApi
    private lateinit var httpClient: OkHttpClient
    private lateinit var gson: Gson

    @Before
    fun setup() {
        httpClient = mockk()
        gson = Gson()
        releasesApi = ReleasesApi(httpClient, gson)
    }

    @Test
    fun `getArtistReleases should return release list on successful response`() =
        runBlocking {
        val artistId = 123
        val responseBody = """
        {
          "pagination": {
            "per_page": 50,
            "pages": 1,
            "page": 1,
            "items": 2
          },
          "releases": [
            {
              "status": "Accepted",
              "thumb": "https://i.discogs.com/njH6c7cGG7rY8fNzW_l0oJtnM8g=/fit-in/150x150/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-16395389-1610987073-7966.jpeg.jpg",
              "title": "The Fat Of The Land",
              "format": "Vinyl, LP, Album",
              "label": "XL Recordings",
              "role": "Main",
              "year": 1997,
              "resource_url": "https://api.discogs.com/releases/16395389-The-Prodigy-The-Fat-Of-The-Land",
              "type": "release",
              "id": 16395389
            },
            {
              "status": "Accepted",
              "thumb": "https://i.discogs.com/0T91C2lY8e2xK2V4z0oq2dRnffg=/fit-in/150x150/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-19623-1263504086.jpeg.jpg",
              "title": "Music For The Jilted Generation",
              "format": "CD, Album",
              "label": "XL Recordings",
              "role": "Main",
              "year": 1994,
              "resource_url": "https://api.discogs.com/releases/19623-The-Prodigy-Music-For-The-Jilted-Generation",
              "type": "release",
              "id": 19623
            }
          ]
        }
        """.trimIndent()

        val request = Request.Builder()
            .url("https://api.discogs.com/artists/$artistId/releases")
            .build()

        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_2)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(MediaType.parse("application/json"), responseBody))
            .build()

        coEvery { httpClient.newCall(any()).execute() } returns response

        val result = releasesApi.getArtistReleases(artistId)

        Assert.assertEquals(2, result.releases.size)
    }

    @Test
    fun `getArtistReleases should throw IOException on unsuccessful response`(): Unit = runBlocking {
        val artistId = 456
        val request = Request.Builder()
            .url("https://api.discogs.com/artists/$artistId/releases")
            .build()
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_2)
            .code(404) // Simulate a "Not Found" response
            .message("Not Found")
            .build()

        coEvery { httpClient.newCall(any()).execute() } returns response

        shouldThrow<IOException> {
            releasesApi.getArtistReleases(artistId)
        }
    }
}