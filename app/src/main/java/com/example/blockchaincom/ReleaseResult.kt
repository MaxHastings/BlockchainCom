package com.example.blockchaincom

sealed class ReleaseResult {
    data class Success(val releases: List<ReleaseModel>) : ReleaseResult()
    data class Error(val message: String, val releases: List<ReleaseModel>) : ReleaseResult()
}