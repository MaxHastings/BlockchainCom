package com.example.blockchaincom.ui.releases

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blockchaincom.features.releases.ReleaseViewModel

@Composable
fun ReleaseListFragment(artistId: Int) {
    val viewModel: ReleaseViewModel = hiltViewModel()
    // Trigger fetching releases when the composable is entered
    LaunchedEffect(Unit) {
        viewModel.getArtistReleases(artistId)
    }

    ReleaseListScreen(viewModel = viewModel)
}