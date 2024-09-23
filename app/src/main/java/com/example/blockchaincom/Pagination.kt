package com.example.blockchaincom

data class Pagination(
    val page: Int,
    val pages: Int,
    val per_page: Int,
    val items: Int,
    val urls: Urls
)