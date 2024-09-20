package com.example.blockchaincom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReleaseItem(release: Release) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = release.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = release.year.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}