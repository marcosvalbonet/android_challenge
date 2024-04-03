package com.ethermail.androidchallenge.domain.usecase

import com.ethermail.androidchallenge.data.CoincapRepository
import com.ethermail.androidchallenge.data.model.markets.MarketsApiData
import javax.inject.Inject

class GetMarketsUseCase @Inject constructor(
    private val coincapRepository : CoincapRepository
) {
    suspend operator fun invoke(): MarketsApiData =
        coincapRepository.getMarkets()
}