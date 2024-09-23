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

sealed class ReleaseUiState(open val releases: List<ReleaseModel> = emptyList()) {
    data object Loading : ReleaseUiState()
    data class Success(override val releases: List<ReleaseModel>) : ReleaseUiState(releases)
    data class Error(override val releases: List<ReleaseModel>, val message: String) : ReleaseUiState()
}

@HiltViewModel
class ReleaseViewModel @Inject constructor(private val getArtistReleasesUseCase: GetArtistReleasesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<ReleaseUiState>(ReleaseUiState.Loading)
    val uiState: StateFlow<ReleaseUiState> = _uiState.asStateFlow()

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    fun getArtistReleases(artistId: Int) {
        viewModelScope.launch {
            when (val result = getArtistReleasesUseCase(artistId)) {
                is ReleaseResult.Success -> {
                    _uiState.value = ReleaseUiState.Success(result.releases)
                }
                is ReleaseResult.Error -> {
                    _errorEvents.emit(result.message)
                    _uiState.value = ReleaseUiState.Error( result.releases, result.message)
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = ReleaseUiState.Success(uiState.value.releases)
    }

}