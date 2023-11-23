package com.viasco.submission.compose.androver.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Favorite : Screen("favorite")
    object Detail : Screen("home/{androVerId}") {
        fun createRoute(androVerId: Int) = "home/$androVerId"
    }
}