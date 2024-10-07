package net.harutiro.trainalert2.core.router

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.harutiro.trainalert2.core.presenter.FirstPage

@Composable
fun MainRouter() {
    val mainNavController = rememberNavController()

    NavHost(navController = mainNavController, startDestination = MainRoute.BOTTOM_NAVIGATION_BAR.route){
        composable(MainRoute.BOTTOM_NAVIGATION_BAR.route){
            FirstPage(
                toDetail = { cityId ->
                    Log.d("MainRouter", "cityId: ${cityId.id}")
                    mainNavController.navigate("${MainRoute.DETAIL.route}/${cityId.id}")
                }
            )
        }
    }
}

enum class MainRoute(val route: String){
    BOTTOM_NAVIGATION_BAR("bottom_navigation_bar"),
    DETAIL("detail")
}