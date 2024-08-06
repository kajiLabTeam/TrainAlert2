package net.harutiro.trainalert2.features.Weather.apis

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import net.harutiro.trainalert2.features.Weather.entities.CityId
import net.harutiro.trainalert2.features.Weather.entities.Weather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NationwideWeatherApiImpl: NationwideWeatherApi{
    override suspend fun getNationwideWeather(cityId:CityId): Weather {
        val client = OkHttpClient.Builder()
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val weatherService = Retrofit.Builder()
            .baseUrl("https://weather.tsukumijima.net")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(NationwideWeatherApiBuilderInterface::class.java)

        val response = weatherService.getWeather(cityId.id)

        return if(response.isSuccessful) {
            Log.d("OkHttp", response.body().toString())
            val weather = response.body()
            weather?.cityId = cityId
            weather ?: Weather(listOf(),"", CityId.tokyo)
        } else {
            Log.d("OkHttp", response.errorBody().toString())
            Weather(listOf(),"" , CityId.tokyo)
        }
    }
}