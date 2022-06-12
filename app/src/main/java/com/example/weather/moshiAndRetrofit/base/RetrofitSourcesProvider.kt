package com.example.weather.moshiAndRetrofit.base

import com.example.weather.moshiAndRetrofit.RetrofitWeatherSource
import com.example.weather.repository.weather.WeatherSource

/**
 * Creating sources based on Retrofit + Moshi.
 */
class RetrofitSourcesProvider(
    private val config: RetrofitConfig
) : SourcesProvider {

    override fun getWeatherRepository(): WeatherSource {
        return RetrofitWeatherSource(config)
    }
}