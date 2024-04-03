package com.ethermail.androidchallenge.ui.screens.assets


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ethermail.androidchallenge.data.model.assets.AssetData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import com.ethermail.androidchallenge.domain.usecase.GetAssetsUseCase
import com.ethermail.androidchallenge.ui.screens.CoroutinesTestRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import org.junit.Assert
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class AssetsViewModelTest {

    private lateinit var viewModel: AssetsViewModel

    @RelaxedMockK
    private lateinit var getAssetsUseCase : GetAssetsUseCase

    @get:Rule
    var rule:CoroutinesTestRule = CoroutinesTestRule()


    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = AssetsViewModel(getAssetsUseCase)

    }

    @After
    fun onAfter() {
    }

    @Test
    fun `retrieveAssets should update AssetsUiState with success`() = runBlockingTest {
        // Given
//        val mockAssets = listOf(
//        )
        val mockAssetsUI = listOf(
            AssetUiItem("BTC", "Bitcoin", "60000.0"),
            AssetUiItem("ETH", "Ethereum", "2000.0")
        )

        //coEvery { getAssetsUseCase() } returns mockAssetsUI

        // When
        viewModel.retrieveAssets()

        // Then
        verify(viewModel.uiState.value == AssetsUiState.Success(mockAssetsUI))
    }

    @Test
    fun `retrieveAssets should update AssetsUiState with error`() = runTest {
        // Given
        coEvery { getAssetsUseCase.invoke() } throws RuntimeException("Error fetching assets")

        // When
        viewModel.retrieveAssets()

        // Then
       // verify(viewModel.uiState.value == AssetsUiState.Error("Error fetching assets"))
        assert(viewModel.uiState.value == AssetsUiState.Error("Error fetching assets"))
    }
}