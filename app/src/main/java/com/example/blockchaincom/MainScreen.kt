package com.example.blockchaincom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(navController: androidx.navigation.NavHostController) {
    var artistId by rememberSaveable { mutableStateOf("108713") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = artistId,
            onValueChange = { artistId = it },
            label = { Text(stringResource(R.string.enter_artist_id)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("release_list/${artistId.toInt()}")
        }) {
            Text(stringResource(R.string.view_releases))
        }
    }
}