package com.example.blockchaincom.data.remote.releases

import com.example.blockchaincom.data.Urls

data class Pagination(
    val page: Int,
    val pages: Int,
    val per_page: Int,
    val items: Int,
    val urls: Urls
)