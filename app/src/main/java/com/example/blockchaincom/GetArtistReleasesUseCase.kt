package com.example.blockchaincom

import javax.inject.Inject

class GetArtistReleasesUseCase @Inject constructor(private val repository: ReleaseRepository) {

    suspend operator fun invoke(artistId: Int): ReleaseResult {
        return repository.getArtistReleases(artistId)
    }
}

