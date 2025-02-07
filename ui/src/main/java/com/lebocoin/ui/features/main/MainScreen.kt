package com.lebocoin.ui.features.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.lebocoin.ui.navigation.AppNavHost
import com.lebocoin.ui.navigation.NavigationItem

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    AppNavHost(
        navController = navController,
        startDestination = NavigationItem.Splash.route
    )
}