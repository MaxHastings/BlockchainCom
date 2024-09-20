package com.example.blockchaincom

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun ReleaseListScreen(releases: List<Release>) {
    LazyColumn {
        items(releases) { release ->
            ReleaseItem(release)
        }
    }
}