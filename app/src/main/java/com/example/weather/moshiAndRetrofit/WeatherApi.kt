package com.example.weather.moshiAndRetrofit

import com.example.weather.moshiAndRetrofit.entities.WeatherApiForm
import retrofit2.http.*

interface WeatherApi{

    @POST()
    suspend fun getWeatherApi(@Url cityName: String): WeatherApiForm

}
