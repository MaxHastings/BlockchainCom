package com.example.blockchaincom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(private val getArtistReleasesUseCase: GetArtistReleasesUseCase) :
    ViewModel() {

    private val _releases = MutableStateFlow<List<Release>>(emptyList())
    val releases: StateFlow<List<Release>> = _releases

    fun getArtistReleases(artistId: Int) {
        viewModelScope.launch {
            _releases.value = getArtistReleasesUseCase(artistId)
        }
    }
}