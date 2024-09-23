package com.example.blockchaincom.ui.releases

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.blockchaincom.data.local.releases.Release

@Composable
fun ReleaseList(releases: List<Release>) {
    LazyColumn {
        items(releases) { release ->
            ReleaseItem(release)
        }
    }
}