package com.viasco.submission.compose.androver

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.viasco.submission.compose.androver.navigation.NavigationItem
import com.viasco.submission.compose.androver.navigation.Screen
import com.viasco.submission.compose.androver.ui.screen.AboutScreen
import com.viasco.submission.compose.androver.ui.screen.DetailScreen
import com.viasco.submission.compose.androver.ui.screen.FavoriteScreen
import com.viasco.submission.compose.androver.ui.screen.HomeScreen


@Composable
fun AndroVer(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { androVerId ->
                        navController.navigate(Screen.Detail.createRoute(androVerId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { androVerId ->
                        navController.navigate(Screen.Detail.createRoute(androVerId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                AboutScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("androVerId") { type = NavType.IntType }
                )
            ) {
                val id = it.arguments?.getInt("androVerId") ?: -1
                DetailScreen(
                    androVerId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.menu_home),
            icon = Icons.Outlined.Home,
            iconSelected = Icons.Filled.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(R.string.menu_favorite),
            icon = Icons.Outlined.FavoriteBorder,
            iconSelected = Icons.Filled.Favorite,
            screen = Screen.Favorite
        ),
        NavigationItem(
            title = stringResource(R.string.menu_profile),
            icon = Icons.Outlined.AccountCircle,
            iconSelected = Icons.Filled.AccountCircle,
            screen = Screen.Profile
        ),
    )

    NavigationBar(
        modifier = modifier
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.screen.route) item.iconSelected else item.icon,
                        contentDescription = item.title
                    )
                },
                selected = currentRoute == item.screen.route,
                label = {
                    Text(
                        item.title,
                        fontWeight = if (currentRoute == item.screen.route) FontWeight.Bold else FontWeight.Normal,
                    )
                },
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}