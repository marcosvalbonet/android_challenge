package com.ethermail.androidchallenge.ui.screens.markets

import com.ethermail.androidchallenge.data.model.markets.MarketData
import com.ethermail.androidchallenge.data.model.markets.MarketsApiData
import com.ethermail.androidchallenge.domain.usecase.GetMarketsUseCase
import com.ethermail.androidchallenge.ui.extensions.formatDate
import com.ethermail.androidchallenge.ui.screens.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MarketsViewModelTest {

    private lateinit var viewModel: MarketViewModel

    @RelaxedMockK
    private lateinit var getMarketsUseCase : GetMarketsUseCase

    @get:Rule
    var rule: CoroutinesTestRule = CoroutinesTestRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = MarketViewModel(getMarketsUseCase)

    }

    @Test
    fun `retrieveMarkets should update MarketsUiState with success`() = runBlockingTest {
        // Given
        val market = MarketData(
            exchangeId = "bibox",
            rank = "76",
            baseSymbol = "BSV",
            baseId = "bitcoin-sv",
            quoteSymbol = "BTC",
            quoteId = "bitcoin",
            priceQuote = "0.0012093000000000",
            percentExchangeVolume = "percentExchangeVolume",
            priceUsd = "80.3713214940290781",
            tradesCount24Hr = "12848",
            updated = 1711035429482,
            volumeUsd24Hr = "645445.1245697122860790"
        )
        val mockMarkets = listOf(
            market
        )
        val marketsApiData = MarketsApiData(marketData = mockMarkets, timestamp = null)

        coEvery { getMarketsUseCase.invoke() } returns marketsApiData

        // When
        viewModel.retrieveMarkets("BTC")

        // Then
        assert(viewModel.uiState.value.market.rank == market.rank)
        assert(viewModel.uiState.value.market.priceUsd == market.priceUsd)
        assert(viewModel.uiState.value.market.updated == market.updated?.formatDate())
    }

}

