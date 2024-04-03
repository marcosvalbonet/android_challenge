package com.ethermail.androidchallenge.domain.usecase

import com.ethermail.androidchallenge.data.CoincapRepository
import com.ethermail.androidchallenge.data.model.assets.AssetsApiData
import javax.inject.Inject

class GetAssetsUseCase @Inject constructor(
    private val coincapRepository : CoincapRepository
) {
    suspend operator fun invoke(): AssetsApiData =
        coincapRepository.getAssets()
}