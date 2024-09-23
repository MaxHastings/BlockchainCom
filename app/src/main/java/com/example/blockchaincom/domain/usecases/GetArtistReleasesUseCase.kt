package com.example.blockchaincom.domain.usecases

import com.example.blockchaincom.features.releases.data.ReleaseRepositoryImpl
import com.example.blockchaincom.features.releases.data.ReleaseResult
import javax.inject.Inject

class GetArtistReleasesUseCase @Inject constructor(private val repository: ReleaseRepositoryImpl) {

    suspend operator fun invoke(artistId: Int): ReleaseResult {
        return repository.getArtistReleases(artistId)
    }
}

