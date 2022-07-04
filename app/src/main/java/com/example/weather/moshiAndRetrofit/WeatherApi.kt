package com.example.weather.moshiAndRetrofit

import com.example.weather.moshiAndRetrofit.entities.WeatherApiForm
import retrofit2.http.*

interface WeatherApi{

    @POST("v1/current.json?key=a6e771c778dc4cd0a26114320221906&")
    suspend fun getWeatherApi(@Query ("q")cityName: String): WeatherApiForm

}
