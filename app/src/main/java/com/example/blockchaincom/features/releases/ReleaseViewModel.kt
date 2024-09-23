package com.example.blockchaincom.features.releases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockchaincom.domain.usecases.GetArtistReleasesUseCase
import com.example.blockchaincom.features.releases.data.ReleaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A ViewModel that handles the retrieval of artist releases and manages the UI state for the releases screen.
 *
 * @param getArtistReleasesUseCase Use case to fetch artist releases.
 * @param dispatcher The dispatcher that determines the thread to run coroutines.
 * See [com.example.blockchaincom.di.DispatcherModule] for details.
 */
@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val getArtistReleasesUseCase: GetArtistReleasesUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    /**
     * Backing property for the UI state that represents the current state of the releases screen.
     * Initially, the state is set to [ReleaseUiState.Loading] and updated based on the result of the
     * artist releases retrieval.
     */
    private val _uiState = MutableStateFlow<ReleaseUiState>(ReleaseUiState.Loading)

    /**
     * Public state exposed as [StateFlow] to represent the current UI state.
     * Observed by the UI layer to update the screen based on changes in the releases data.
     */
    val uiState: StateFlow<ReleaseUiState> = _uiState.asStateFlow()

    /**
     * Backing property for error events represented as a [MutableSharedFlow] that can be emitted
     * when an error occurs during data fetching.
     */
    private val _errorEvents = MutableSharedFlow<String>()

    /**
     * Publicly exposed [SharedFlow] for error events.
     */
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    /**
     * Fetches the releases for a given artist identified by [artistId].
     *
     * This function launches a coroutine and calls the [GetArtistReleasesUseCase]
     * to retrieve the data. Depending on the result, the UI state is updated as follows:
     *
     * - On success: The state is set to [ReleaseUiState.Success] with the list of releases.
     * - On error: The error message is emitted to [_errorEvents], and the state is updated to [ReleaseUiState.Error].
     *
     * @param artistId The ID of the artist whose releases are being fetched.
     */
    fun getArtistReleases(artistId: Int) {
        viewModelScope.launch(dispatcher) {
            when (val result = getArtistReleasesUseCase(artistId)) {
                is ReleaseResult.Success -> {
                    _uiState.value = ReleaseUiState.Success(result.releases)
                }
                is ReleaseResult.Error -> {
                    _errorEvents.emit(result.message)
                    _uiState.value = ReleaseUiState.Error(result.releases, result.message)
                }
            }
        }
    }

    /**
     * Clears any current error in the UI state by restoring the state to [ReleaseUiState.Success]
     * with the current list of releases. This is called after an error has been dismissed by the user.
     */
    fun clearError() {
        _uiState.value = ReleaseUiState.Success(uiState.value.releases)
    }
}
