package com.example.blockchaincom.domain.usecases

import com.example.blockchaincom.features.releases.data.ReleaseRepository
import com.example.blockchaincom.features.releases.data.ReleaseResult
import javax.inject.Inject

class GetArtistReleasesUseCase @Inject constructor(private val repository: ReleaseRepository) {

    suspend operator fun invoke(artistId: Int): ReleaseResult {
        return repository.getArtistReleases(artistId)
    }
}

