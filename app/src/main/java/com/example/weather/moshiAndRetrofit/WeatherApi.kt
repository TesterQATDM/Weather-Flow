package com.example.weather.moshiAndRetrofit

import com.example.weather.moshiAndRetrofit.entities.WeatherApiForm
import retrofit2.http.*

interface WeatherApi{

    @POST("current.json?key=4af1e205156743f9af6231933222005&")
    suspend fun getWeatherApi(@Query ("q")cityName: String): WeatherApiForm

}
