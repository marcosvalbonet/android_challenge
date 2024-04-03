package com.ethermail.androidchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ethermail.androidchallenge.ui.screens.assets.AssetsScreen
import com.ethermail.androidchallenge.ui.screens.markets.MarketScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.AssetsScreen.route){
        composable(route= AppScreens.AssetsScreen.route){
            AssetsScreen(navController = navController)
        }
        composable(route= AppScreens.MarketScreen.route+"/{symbol}"){navBackStackEntry ->
            val symbol = navBackStackEntry.arguments?.getString("symbol")
            MarketScreen(symbol)
        }
    }
}