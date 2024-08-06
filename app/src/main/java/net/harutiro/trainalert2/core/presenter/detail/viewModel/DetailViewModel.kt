package net.harutiro.trainalert2.core.presenter.detail.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.harutiro.trainalert2.features.Weather.entities.CityId
import net.harutiro.trainalert2.features.Weather.entities.Weather
import net.harutiro.trainalert2.features.Weather.repositories.NationwideWeatherRepository
import net.harutiro.trainalert2.features.Weather.repositories.NationwideWeatherRepositoryImpl
import net.harutiro.trainalert2.features.favoriteDB.repositories.WeatherFavoriteRepository
import net.harutiro.trainalert2.features.favoriteDB.repositories.WeatherFavoriteRepositoryImpl

class DetailViewModel(
    val nationwideWeatherRepository: NationwideWeatherRepository = NationwideWeatherRepositoryImpl(),
    val weatherFavoriteRepository: WeatherFavoriteRepository = WeatherFavoriteRepositoryImpl()
) : ViewModel() {
    val weather = mutableStateOf<Weather?>(null)
    val city = mutableStateOf<CityId?>(null)
    val bookmark = mutableStateOf(false)

    fun getWeather(cityId: CityId) {

        Log.d("DetailViewModel", "cityId: $cityId")

        viewModelScope.launch(Dispatchers.IO) {
            // 参照渡しを行い、実際にはRepository側でweathersに追加している
            weather.value = nationwideWeatherRepository.getPrefectureWeather(cityId)
            city.value = cityId
            bookmark.value = weatherFavoriteRepository.isFavorite(cityId).await()
        }
    }

    fun updateBookmark(
        showSnackBar: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO){
            bookmark.value = !bookmark.value
            if (bookmark.value) {
                weatherFavoriteRepository.insertFavorite(city.value!!).await()
                showSnackBar("お気に入りに追加しました")
            } else {
                weatherFavoriteRepository.deleteFavorite(city.value!!).await()
                showSnackBar("お気に入りから削除しました")
            }
        }
    }
}