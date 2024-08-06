package net.harutiro.trainalert2.core.presenter

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import net.harutiro.trainalert2.R
import net.harutiro.trainalert2.core.entities.BottomNavigationItem
import net.harutiro.trainalert2.core.presenter.component.BottomNavigationBar
import net.harutiro.trainalert2.core.presenter.favorite.page.FavoritePage
import net.harutiro.trainalert2.core.presenter.home.page.HomePage
import net.harutiro.trainalert2.core.router.BottomNavigationBarRoute
import net.harutiro.trainalert2.core.router.BottomNavigationBarRouter
import net.harutiro.trainalert2.core.utils.DateUtils
import net.harutiro.trainalert2.features.Weather.entities.CityId
import net.harutiro.trainalert2.features.favoriteDB.repositories.WeatherFavoriteRepository
import net.harutiro.trainalert2.features.favoriteDB.repositories.WeatherFavoriteRepositoryImpl

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun FirstPage(
    toDetail: (cityId: CityId) -> Unit,
    weatherFavoriteRepository: WeatherFavoriteRepository = WeatherFavoriteRepositoryImpl()
){

    var selectedItemIndex by remember { mutableIntStateOf(0) }
    var isStarted by remember { mutableStateOf(false) }

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
            path = BottomNavigationBarRoute.FAVORITE
        )
    )

    LaunchedEffect(weatherFavoriteRepository) {
        if(isStarted){
            val favoriteList = weatherFavoriteRepository.getFavoriteList().await()
            if (favoriteList.isNotEmpty()) {
                selectedItemIndex = 1
            }
            isStarted = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                val now = DateUtils.getNowString()
                Text(text = "$now の全国天気") // 今日の日付(曜日)　の天気(

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

        BottomNavigationBarRouter(
            homePage = {
                HomePage(toDetail = toDetail)
            },
            favoritePage = {
                FavoritePage()
            },
            navController = navController,
            startDestination = bottomNavigationItems[selectedItemIndex].path.route,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}