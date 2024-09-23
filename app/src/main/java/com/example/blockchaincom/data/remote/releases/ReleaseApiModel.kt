package com.example.blockchaincom.data.remote.releases

import com.google.gson.annotations.SerializedName

data class ReleaseApiModel(
    val id: Int,
    val title: String,
    val type: String,
    @SerializedName("main_release") val mainRelease: Int?,
    val artist: String,
    val role: String,
    @SerializedName("resource_url") val resourceUrl: String,
    val year: Int?,
    val thumb: String?,
    val status: String?,
    val format: String?,
    val label: String?
)