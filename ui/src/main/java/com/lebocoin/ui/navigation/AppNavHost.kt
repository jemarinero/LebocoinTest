package com.lebocoin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lebocoin.domain.model.ErrorType
import com.lebocoin.ui.features.infolist.InformationScreen
import com.lebocoin.ui.features.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Splash.route,
    onError: (errorType: ErrorType?) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen(
                onError = { onError.invoke(it) }
            ) {
                navController.navigate(NavigationItem.Home.route)
            }
        }
        composable(NavigationItem.Home.route) {
            InformationScreen {
                onError.invoke(it)
            }
        }
    }
}