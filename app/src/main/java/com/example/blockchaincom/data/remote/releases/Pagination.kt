package com.example.blockchaincom.data.remote.releases

import com.google.gson.annotations.SerializedName

data class Pagination(
    val page: Int,
    val pages: Int,
    @SerializedName("per_page") val perPage: Int,
    val items: Int,
    val urls: Urls
)