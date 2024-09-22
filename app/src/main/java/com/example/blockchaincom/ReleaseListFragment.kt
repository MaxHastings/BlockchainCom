package com.example.blockchaincom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReleaseListFragment(artistId: Int) {
    val viewModel: ReleaseViewModel = hiltViewModel()
    // Trigger fetching releases when the composable is entered
    LaunchedEffect(Unit) {
        viewModel.getArtistReleases(artistId)
    }

    ReleaseListScreen(viewModel = viewModel)
}