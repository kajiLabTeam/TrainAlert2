package net.harutiro.trainalert2.core.router

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBarRouter(
    homePage: @Composable () -> Unit,
    favoritePage: @Composable () -> Unit,
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
            .fillMaxSize()
    ) {
        composable(BottomNavigationBarRoute.HOME.route) {
            homePage()
        }
        composable(BottomNavigationBarRoute.MAP.route) {
            favoritePage()
        }
    }
}

enum class BottomNavigationBarRoute(val route: String) {
    HOME("home"),
    MAP("map")
}

