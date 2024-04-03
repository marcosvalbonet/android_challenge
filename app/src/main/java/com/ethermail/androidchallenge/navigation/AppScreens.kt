package com.ethermail.androidchallenge.navigation

sealed class AppScreens (val route: String){
    data object AssetsScreen: AppScreens("assets_screen")
    data object MarketScreen: AppScreens("market_screen")

}