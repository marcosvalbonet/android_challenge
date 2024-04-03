package com.ethermail.androidchallenge.ui.screens.assets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ethermail.androidchallenge.domain.usecase.GetAssetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetsViewModel @Inject constructor(
    private val getAssetsUseCase : GetAssetsUseCase
) : ViewModel(){

    private val _uiState:MutableStateFlow<AssetsUiState> = MutableStateFlow(AssetsUiState.Default)
    val uiState: StateFlow<AssetsUiState> = _uiState

    init {
        retrieveAssets()
    }

    fun retrieveAssets(){
        viewModelScope.launch {
            _uiState.value = AssetsUiState.Loading
            val assetsUI = getAssetsUseCase().assetData?.
                map {  AssetUiItem(
                    symbol = it.symbol ?: "N/A",
                    name = it.name ?: "N/A",
                    price = it.priceUsd ?: "0.0"
                    )
                } ?: emptyList()

            if (assetsUI.isEmpty()) _uiState.value =  AssetsUiState.Error("Error fetching assets")
            else _uiState.value =  AssetsUiState.Success(assetsUI)
        }
    }
}

sealed interface AssetsUiState {
    data object Loading: AssetsUiState
    data class Error(val msg: String?) : AssetsUiState
    data object Default : AssetsUiState
    data class Success(
        val assets: List<AssetUiItem>?
    ): AssetsUiState

}