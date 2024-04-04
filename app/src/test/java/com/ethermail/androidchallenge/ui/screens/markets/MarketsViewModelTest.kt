package com.ethermail.androidchallenge.ui.screens.markets

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ethermail.androidchallenge.data.model.markets.MarketData
import com.ethermail.androidchallenge.data.model.markets.MarketsApiData
import com.ethermail.androidchallenge.domain.usecase.GetMarketsUseCase
import com.ethermail.androidchallenge.ui.extensions.formatDate
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MarketsViewModelTest2 {

    private lateinit var viewModel: MarketViewModel


    private lateinit var getMarketsUseCase : GetMarketsUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        getMarketsUseCase = mockk()
        viewModel = MarketViewModel(getMarketsUseCase)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun shutdown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `retrieveMarkets should update MarketsUiState with success`() = runTest {
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
        viewModel.retrieveMarkets("BSV")

        // Then
        assert(viewModel.uiState.value.market.rank == market.rank)
        assert(viewModel.uiState.value.market.priceUsd == market.priceUsd)
        assert(viewModel.uiState.value.market.updated == market.updated?.formatDate())
    }

}

