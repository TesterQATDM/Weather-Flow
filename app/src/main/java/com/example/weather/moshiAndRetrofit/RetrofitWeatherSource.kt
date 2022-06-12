package com.example.weather.moshiAndRetrofit

import com.example.weather.moshiAndRetrofit.base.BaseRetrofitSource
import com.example.weather.moshiAndRetrofit.base.RetrofitConfig
import com.example.weather.moshiAndRetrofit.entities.WeatherApiForm
import com.example.weather.repository.weather.WeatherSource
import kotlinx.coroutines.delay

class RetrofitWeatherSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), WeatherSource {

    private val weatherApi = retrofit.create(WeatherApi::class.java)

    override suspend fun getWeather(cityName: String): WeatherApiForm  = wrapRetrofitExceptions {
        delay(1000)
        weatherApi.getWeatherApi(cityName)
    }
}