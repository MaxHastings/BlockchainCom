package com.example.blockchaincom

import javax.inject.Inject

class GetArtistReleasesUseCase @Inject constructor(private val repository: DiscogsRepository) {

    suspend operator fun invoke(artistId: Int): List<Release> {
        return repository.fetchArtistReleases(artistId).releases
    }
}