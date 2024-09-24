package com.example.blockchaincom.ui.main

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
import androidx.navigation.NavHostController
import com.example.blockchaincom.R

@Composable
fun MainScreen(navController: NavHostController) {
    var artistId by rememberSaveable { mutableStateOf("108713") }
    var isValidId by rememberSaveable { mutableStateOf(true) }
    val maxLength = 10 // Example maximum length for artist ID

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = artistId,
            onValueChange = {
                artistId = it.filter { it.isDigit() }.take(maxLength) // Only allow digits
                isValidId = artistId.isNotEmpty()
            },
            label = { Text(stringResource(R.string.enter_artist_id)) },
            isError = !isValidId,
            supportingText = {
                if (!isValidId) {
                    Text("Please enter a valid artist ID (only numbers)")
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (isValidId) {
                navController.navigate("release_list/${artistId}")
            }
        }, enabled = isValidId) { // Disable button if ID is invalid
            Text(stringResource(R.string.view_releases))
        }
    }
}