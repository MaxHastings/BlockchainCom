package com.example.blockchaincom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReleaseModel(
    @PrimaryKey val id: Int,
    val artistId: Int,
    val title: String,
    val type: String,
    val main_release: Int?,
    val artist: String,
    val role: String,
    val resource_url: String,
    val year: Int?,
    val thumb: String?,
    val status: String?,    // for releases that contain the 'status' field
    val format: String?,    // for releases that contain the 'format' field
    val label: String?      // for releases that contain the 'label' field
)