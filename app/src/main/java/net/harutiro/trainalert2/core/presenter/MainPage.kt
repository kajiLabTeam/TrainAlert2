package net.harutiro.trainalert2.core.presenter

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.harutiro.trainalert2.R
import net.harutiro.trainalert2.core.entities.BottomNavigationItem
import net.harutiro.trainalert2.core.presenter.component.BottomNavigationBar
import net.harutiro.trainalert2.core.router.BottomNavigationBarRoute
import net.harutiro.trainalert2.core.router.MainRouter

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun FirstPage(){

    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()
    var topBarTitle by remember {
        mutableStateOf("TrainAlert2")
    }

    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.home),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Filled.Home,
            hasNews = false,
            badgeCount = null,
            path = BottomNavigationBarRoute.HOME
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.map),
            selectedIcon = Icons.Filled.Map,
            unselectedIcon = Icons.Filled.Map,
            hasNews = false,
            badgeCount = null,
            path = BottomNavigationBarRoute.MAP
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = topBarTitle)
            })
        },
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if(
                currentRoute == BottomNavigationBarRoute.HOME.route ||
                currentRoute == BottomNavigationBarRoute.MAP.route
            ){
                BottomNavigationBar(
                    items = bottomNavigationItems,
                    selectedItemIndex = selectedItemIndex
                ) { index ->
                    selectedItemIndex = index
                    navController.navigate(bottomNavigationItems[index].path.route)
                }
            }
        }
    ) { innerPadding ->
        MainRouter(
            changeTopBarTitle = { title ->
                topBarTitle = title
            },
            toRouteEditorScreen = {
                navController.navigate(BottomNavigationBarRoute.ROUTE_EDITOR.route)
            },
            toBackScreen = {
                navController.popBackStack()
            },
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}