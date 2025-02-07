package com.lebocoin.ui.navigation

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object Home : NavigationItem(Screen.HOME.name)
}

enum class Screen {
    SPLASH,
    HOME
}