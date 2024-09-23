package com.example.blockchaincom.features.releases

import com.example.blockchaincom.data.local.releases.Release

sealed class ReleaseUiState(open val releases: List<Release> = emptyList()) {
    data object Loading : ReleaseUiState()
    data class Success(override val releases: List<Release>) : ReleaseUiState(releases)
    data class Error(override val releases: List<Release>, val message: String) : ReleaseUiState()
}