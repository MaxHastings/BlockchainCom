package com.example.blockchaincom

data class PaginationResponse(
    val pagination: Pagination,
    val releases: List<ReleaseApiModel>
)