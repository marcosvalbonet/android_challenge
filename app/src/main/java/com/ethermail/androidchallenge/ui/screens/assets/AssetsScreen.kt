package com.ethermail.androidchallenge.ui.screens.assets

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ethermail.androidchallenge.navigation.AppScreens

@Composable
fun AssetsScreen(
     navController: NavController,
    assetsViewModel: AssetsViewModel = hiltViewModel()
) {
    val uiState = assetsViewModel.uiState.collectAsState()

    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(top = 4.dp),
    ) {
      when (val assetsState = uiState.value) {
          is AssetsUiState.Success -> {
              items(count = assetsState.assets!!.size, key = { index -> assetsState.assets[index].symbol }) { index ->
                  AssetView(navController, asset = assetsState.assets[index])
              }
          }
          is AssetsUiState.Loading -> {}
          is AssetsUiState.Error -> { /*Toast.makeText(LocalContext.current, assetsState.msg, Toast.LENGTH_SHORT).show()*/}
          is AssetsUiState.Default -> {}
      }
    }
}

@Composable
fun AssetView(navController: NavController, asset: AssetUiItem) = Card(
    shape = RoundedCornerShape(10),
    modifier = Modifier.fillMaxWidth(),
) {
    Column(modifier = Modifier
        .padding(8.dp)
        .clickable {
            navController.navigate(AppScreens.MarketScreen.route +"/"+ asset.symbol)
    },) {
        Text(text = asset.name)
        Row {
            Text(text = asset.symbol, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = asset.price)
        }
    }
}

@Preview
@Composable
private fun PreviewAssetView() {
    AssetsScreen(navController = rememberNavController())
}