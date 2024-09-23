package com.example.blockchaincom

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReleaseListScreen(viewModel: ReleaseViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()


    when (uiState) {
        is ReleaseUiState.Loading -> {
            CircularProgressIndicator() // Show a loading indicator
        }
        is ReleaseUiState.Error -> {
            AlertDialog(
                onDismissRequest = { viewModel.clearError() },
                title = { Text(stringResource(R.string.error)) },
                text = { Text((uiState as ReleaseUiState.Error).message) },
                confirmButton = {
                    TextButton(onClick = { viewModel.clearError() }) {
                        Text(stringResource(R.string.ok))
                    }
                }
            )
        }
        is ReleaseUiState.ListState -> {}
    }
    ReleaseList(releases = uiState.releases)
}

@Composable
fun ReleaseList(releases: List<ReleaseModel>) {
    LazyColumn {
        items(releases) { release ->
            ReleaseItem(release)
        }
    }
}