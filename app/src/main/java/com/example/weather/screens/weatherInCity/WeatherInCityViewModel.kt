package com.example.weather.screens.weatherInCity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.moshiAndRetrofit.entities.WeatherApiForm
import com.example.weather.repository.Singletons
import com.example.weather.repository.weather.moshiAndWeatherApi.RetrofitWeatherRepository
import com.example.weather.screens.base.BaseViewModel
import com.example.weather.utils.logger.LogCatLogger
import com.example.weather.utils.logger.Logger
import com.example.weather.utils.share

class WeatherInCityViewModel(
    weatherRepository: RetrofitWeatherRepository = Singletons.retrofitWeatherRepository,
    logger: Logger = LogCatLogger
): BaseViewModel(weatherRepository, logger)  {

    private val _weatherFromRetrofit = MutableLiveData<WeatherApiForm>()
    val weatherFromRetrofit = _weatherFromRetrofit.share()

    fun loadCity(cityName: String) {
        viewModelScope.safeLaunch {
            _weatherFromRetrofit.value = weatherRepository.getRetrofitWeatherRepository(cityName)
        }
    }
}