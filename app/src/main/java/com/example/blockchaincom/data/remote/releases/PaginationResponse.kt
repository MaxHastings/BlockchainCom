package com.example.blockchaincom.data.remote.releases

data class PaginationResponse(
    val pagination: Pagination,
    val releases: List<ReleaseApiModel>
)