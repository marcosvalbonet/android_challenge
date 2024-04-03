package com.ethermail.androidchallenge.ui.screens.markets

data class MarketUiState (
    val isLoading: Boolean = false,
    val market: MarketUiItem = MarketUiItem()
)