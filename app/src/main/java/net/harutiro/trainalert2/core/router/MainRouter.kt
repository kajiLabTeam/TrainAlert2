package net.harutiro.trainalert2.core.router

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.harutiro.trainalert2.core.presenter.FirstPage
import net.harutiro.trainalert2.core.presenter.home.HomeScreen
import net.harutiro.trainalert2.core.presenter.map.MapScreen

@Composable
fun MainRouter(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationBarRoute.HOME.route,
        modifier = modifier
            .fillMaxSize()
    ) {
        composable(BottomNavigationBarRoute.HOME.route) {
            HomeScreen()
        }
        composable(BottomNavigationBarRoute.MAP.route) {
            MapScreen()
        }
    }
}

enum class BottomNavigationBarRoute(val route: String) {
    HOME("home"),
    MAP("map")
}