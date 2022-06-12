package com.example.weather.moshiAndRetrofit.base

import com.example.weather.repository.weather.WeatherSource

/**
 * Factory class for all network sources.
 */
interface SourcesProvider {

    fun getWeatherRepository(): WeatherSource

}