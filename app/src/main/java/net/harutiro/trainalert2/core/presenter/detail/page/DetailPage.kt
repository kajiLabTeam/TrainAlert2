package net.harutiro.trainalert2.core.presenter.detail.page

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.harutiro.trainalert2.core.utils.DateUtils
import net.harutiro.trainalert2.core.widgets.ArrowBackButton
import net.harutiro.trainalert2.features.Weather.entities.CityId
import net.harutiro.trainalert2.features.Weather.entities.Weather
import net.harutiro.trainalert2.core.presenter.detail.viewModel.DetailViewModel
import net.harutiro.trainalert2.core.presenter.component.BookmarkButton
import java.lang.Double.NaN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(toBottomNavigationBar: () -> Unit, cityId: CityId , viewModel: DetailViewModel = viewModel()) {

    // スナックバーの表示
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    viewModel.city.value = cityId

    Log.d("DetailPage", "cityId: $cityId")

    LaunchedEffect(key1 = viewModel.city) {
        viewModel.getWeather(cityId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
             TopAppBar(
                 navigationIcon = {
                     ArrowBackButton {
                         toBottomNavigationBar()
                     }
                 },
                 title = {
                     val cityName = Weather.getCityAcquisition(viewModel.weather.value?.title ?: "")
                     Text(text = "${cityName}の3日間の天気")
                 },
                 actions = {
                     BookmarkButton(
                         isBookmark = viewModel.bookmark.value,
                     ) {
                         viewModel.updateBookmark {
                             scope.launch {
                                 // スナックバーが表示された後にスナックバーが呼ばれたら前のスナックバーをキャンセルする
                                hostState.currentSnackbarData?.dismiss()
                                hostState.showSnackbar(it)
                             }
                         }
                     }
                 },

             )
        }
    ) { padding ->
        Detail3DaysList(padding = padding)
    }


}

@Composable
fun Detail3DaysList(
    viewModel: DetailViewModel = viewModel(),
    padding: PaddingValues,
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(padding)
    ) {
        items(viewModel.weather.value?.forecasts ?: emptyList()) {
            DetailWeatherCell(
                imageUrl = it.image.url,
                tempMax = it.temperature.max.celsius ?: NaN,
                tempMin = it.temperature.min.celsius ?: NaN,
                date = DateUtils.apiDateToJapaneseNotation(it.date),
            )
        }
    }
}
