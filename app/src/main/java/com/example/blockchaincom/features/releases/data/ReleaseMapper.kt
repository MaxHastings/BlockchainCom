package com.example.blockchaincom.features.releases.data

import com.example.blockchaincom.data.local.releases.Release
import com.example.blockchaincom.data.remote.releases.ReleaseApiModel
import javax.inject.Inject

class ReleaseMapper @Inject constructor() {
    fun mapApiModelsToModels(apiModels: List<ReleaseApiModel>, artistId: Int): List<Release> {
        return apiModels.map { apiModel ->
            Release(
                id = apiModel.id,
                artistId = artistId,
                title = apiModel.title,
                type = apiModel.type,
                main_release = apiModel.main_release,
                artist = apiModel.artist,
                role = apiModel.role,
                resource_url = apiModel.resource_url,
                year = apiModel.year,
                thumb = apiModel.thumb,
                status = apiModel.status,
                format = apiModel.format,
                label = apiModel.label
            )
        }
    }
}