package com.example.weather.repository.weather.moshiAndWeatherApi

import com.example.weather.moshiAndRetrofit.entities.WeatherApiForm
import com.example.weather.repository.weather.WeatherSource

class RetrofitWeatherRepository(
    private val weatherSource: WeatherSource
){

    suspend fun getRetrofitWeatherRepository(cityName: String): WeatherApiForm{
        return weatherSource.getWeather(cityName)
    }
}