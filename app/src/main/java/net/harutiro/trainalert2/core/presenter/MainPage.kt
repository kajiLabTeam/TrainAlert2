package net.harutiro.trainalert2.core.presenter

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
            title = stringResource(id = R.string.favorite),
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Filled.Favorite,
            hasNews = false,
            badgeCount = null,
            path = BottomNavigationBarRoute.MAP
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "TrainAlert2")
            })
        },
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavigationItems,
                selectedItemIndex = selectedItemIndex
            ) { index ->
                selectedItemIndex = index
                navController.navigate(bottomNavigationItems[index].path.route)
            }
        }
    ) { innerPadding ->
        MainRouter(
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}