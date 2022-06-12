package com.example.weather.repository.weather

import com.example.weather.moshiAndRetrofit.entities.WeatherApiForm

interface WeatherSource{

    suspend fun getWeather(cityName: String): WeatherApiForm

}