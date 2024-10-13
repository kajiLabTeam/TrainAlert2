package net.harutiro.trainalert2.core.router

import android.icu.text.CaseMap.Title
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
import net.harutiro.trainalert2.core.presenter.routeEditer.RouteEditScreen

@Composable
fun MainRouter(
    changeTopBarTitle:(String) -> Unit,
    toRouteEditorScreen: () -> Unit,
    toBackScreen: () -> Unit,
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
            HomeScreen(
                toRouteEditor = toRouteEditorScreen
            )
            changeTopBarTitle(BottomNavigationBarRoute.HOME.title)
        }
        composable(BottomNavigationBarRoute.MAP.route) {
            MapScreen()
            changeTopBarTitle(BottomNavigationBarRoute.MAP.title)
        }
        composable(BottomNavigationBarRoute.ROUTE_EDITOR.route) {
            RouteEditScreen(
                toBackScreen = toBackScreen
            )
            changeTopBarTitle(BottomNavigationBarRoute.ROUTE_EDITOR.title)
        }
    }
}

enum class BottomNavigationBarRoute(val route: String,val title:String) {
    HOME("home","ホーム"),
    MAP("map","マップ"),
    ROUTE_EDITOR("route_editor","ルート編集"),
}