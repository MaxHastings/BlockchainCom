package com.example.blockchaincom

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun ReleaseList(releases: List<ReleaseModel>) {
    LazyColumn {
        items(releases) { release ->
            ReleaseItem(release)
        }
    }
}