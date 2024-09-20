package com.example.blockchaincom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReleaseListFragment(artistId: Int) {
    val viewModel: ReleaseViewModel = hiltViewModel()
    val releases = viewModel.releases.collectAsState()

    // Trigger fetching releases when the composable is entered
    LaunchedEffect(Unit) {
        viewModel.getArtistReleases(artistId)
    }

    ReleaseListScreen(releases = releases.value)
}