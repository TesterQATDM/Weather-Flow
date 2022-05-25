package com.example.weather.screens.weatherInCity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.repository.city.CityRepository
import com.example.weather.repository.city.room.entities.City
import com.example.weather.utils.share

class WeatherInCityViewModel(
    private val cityRepository: CityRepository
): ViewModel() {
    private val _cityD = MutableLiveData<City>()
    val cityD = _cityD.share()

    private val _weatherAPI = MutableLiveData<Weather>()
    val weatherAPI= _weatherAPI.share()

    fun loadWeather(weather: Weather){
        _weatherAPI.value= weather
    }

    fun loadCity(city: City) {
        _cityD.value = city
    }
}