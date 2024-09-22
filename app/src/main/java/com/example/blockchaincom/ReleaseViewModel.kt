package com.example.blockchaincom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReleaseUiState(
    val releases: List<Release> = emptyList(),
    val showError: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ReleaseViewModel @Inject constructor(private val getArtistReleasesUseCase: GetArtistReleasesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ReleaseUiState())
    val uiState: StateFlow<ReleaseUiState> = _uiState.asStateFlow()

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    fun getArtistReleases(artistId: Int) {
        viewModelScope.launch {
            when (val result = getArtistReleasesUseCase(artistId)) {
                is ReleaseResult.Success -> {
                    _uiState.value = _uiState.value.copy(releases = result.releases)
                }
                is ReleaseResult.Error -> {
                    _errorEvents.emit("Error: ${result.message}")
                    _uiState.value = _uiState.value.copy(showError = true, errorMessage = result.message)
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(showError = false, errorMessage = null)
    }

}