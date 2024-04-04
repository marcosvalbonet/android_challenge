package com.ethermail.androidchallenge.ui.screens.assets


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ethermail.androidchallenge.data.model.assets.AssetData
import com.ethermail.androidchallenge.data.model.assets.AssetsApiData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import io.mockk.coEvery
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.ethermail.androidchallenge.domain.usecase.GetAssetsUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AssetsViewModelTest {

    private lateinit var viewModel: AssetsViewModel

    private lateinit var getAssetsUseCase : GetAssetsUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        getAssetsUseCase = mockk()
        viewModel = AssetsViewModel(getAssetsUseCase)

    }

    @After
    fun onAfter() {
    }

    @Test
    fun `retrieveAssets should update AssetsUiState with success`() = runTest {
        // Given
        val mockAssets = listOf(
            AssetData(
                 changePercent24Hr= "24",
                 explorer="",
                 id="BTC",
                 marketCapUsd="",
                 maxSupply="",
                 name = "Bitcoin",
                 priceUsd="60000.0",
                 rank="",
                 supply= "BTC",
                 symbol="BTC",
                 volumeUsd24Hr="",
                 vwap24Hr=""
            ),AssetData(
                changePercent24Hr= "24",
                explorer="",
                id="ETH",
                marketCapUsd="",
                maxSupply="",
                name = "Ethereum",
                priceUsd="2000.0",
                rank="",
                supply= "ETH",
                symbol="ETH",
                volumeUsd24Hr="",
                vwap24Hr=""
                )
        )

        val mockAssetsUI = listOf(
            AssetUiItem("BTC", "Bitcoin", "60000.0"),
            AssetUiItem("ETH", "Ethereum", "2000.0")
        )

        val assetsApiData = AssetsApiData(assetData = mockAssets, timestamp = null)

        coEvery { getAssetsUseCase() } returns assetsApiData

        // When
        viewModel.retrieveAssets()

        // Then
        assert(viewModel.uiState.value is AssetsUiState.Success)
    }

    @Test
    fun `retrieveAssets should update AssetsUiState with error`() = runTest {
        // Given

        val assetsApiData = AssetsApiData(assetData = emptyList(), timestamp = null)
        coEvery { getAssetsUseCase() } returns assetsApiData

        // When
        viewModel.retrieveAssets()

        // Then
        assert(viewModel.uiState.value is AssetsUiState.Error)
    }
}