package com.ethermail.androidchallenge.data

import com.ethermail.androidchallenge.data.model.assets.AssetsApiData
import com.ethermail.androidchallenge.data.model.markets.MarketsApiData

class CoincapRepository(
    private val service: CoincapService
) {
    suspend fun getAssets(): AssetsApiData = service.getAssets()

    suspend fun getMarkets(): MarketsApiData = service.getMarkets()
}