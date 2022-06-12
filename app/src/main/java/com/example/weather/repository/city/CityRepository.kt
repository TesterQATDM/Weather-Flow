package com.example.weather.repository.city

import com.example.weather.repository.Repository
import kotlinx.coroutines.flow.Flow

interface CityRepository: Repository {

    suspend fun getAvailableCityRoom(): List<String>

    suspend fun deleteCityRoom(cityName: String)

    suspend fun addCity(cityName: String)

    fun listenerCurrentListCities(): Flow<List<String>>

}