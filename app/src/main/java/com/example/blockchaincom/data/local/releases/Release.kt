package com.example.blockchaincom.data.local.releases

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Release(
    @PrimaryKey val id: Int,
    val artistId: Int,
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