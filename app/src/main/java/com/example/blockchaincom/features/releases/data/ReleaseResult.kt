package com.example.blockchaincom.features.releases.data

import com.example.blockchaincom.data.local.releases.Release

sealed class ReleaseResult {
    data class Success(val releases: List<Release>) : ReleaseResult()
    data class Error(val message: String, val releases: List<Release>) : ReleaseResult()
}