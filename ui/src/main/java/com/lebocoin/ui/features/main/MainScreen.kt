package com.lebocoin.ui.features.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.lebocoin.domain.model.ErrorType
import com.lebocoin.ui.components.ErrorComponent
import com.lebocoin.ui.navigation.AppNavHost
import com.lebocoin.ui.navigation.NavigationItem

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var errorType by remember { mutableStateOf<ErrorType?>(null) }
    AppNavHost(
        navController = navController,
        startDestination = NavigationItem.Splash.route
    ) {
        errorType = it
    }

    ErrorComponent(errorType) {
        errorType = null
    }
}