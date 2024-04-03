package com.ethermail.androidchallenge.ui.screens.markets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ethermail.androidchallenge.domain.usecase.GetMarketsUseCase
import com.ethermail.androidchallenge.ui.extensions.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getMarketsUseCase : GetMarketsUseCase
) : ViewModel(){

    private val _uiState: MutableStateFlow<MarketUiState> = MutableStateFlow(MarketUiState())
    val uiState: StateFlow<MarketUiState> = _uiState

//    init {
//        retrieveMarkets("UTC")
//    }

    fun retrieveMarkets(symbol : String){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val marketUI = getMarketsUseCase().marketData?.
            find { it.baseSymbol == symbol}?.
            let { market ->
                MarketUiItem(
                    market.exchangeId ?: "0",
                    market.rank ?: "0",
                    market.priceUsd ?: "0",
                    market.updated?.formatDate() ?: "0"
                )
            } ?: MarketUiItem(
                 "0",
                 "0",
                 "0",
                 "0"
            )
            _uiState.value = _uiState.value.copy(market = marketUI, isLoading = false)
        }
    }

}
