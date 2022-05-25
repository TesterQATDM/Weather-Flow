package com.example.weather.repository

import com.example.weather.repository.account.AccountInMemory
import com.example.weather.repository.account.AccountRepository
import com.example.weather.repository.city.CityInMemory
import com.example.weather.repository.city.CityRepository

object Repositories {

    val accountRepository: AccountRepository = AccountInMemory()
    val cityRepository: CityRepository = CityInMemory()
    //val weatherRepository: WeatherRepository = WeatherInMemory()
}