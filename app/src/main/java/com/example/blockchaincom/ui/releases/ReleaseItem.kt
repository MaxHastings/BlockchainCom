package com.example.blockchaincom.ui.releases

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blockchaincom.data.local.releases.Release

@Composable
fun ReleaseItem(release: Release) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = release.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = release.year.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}