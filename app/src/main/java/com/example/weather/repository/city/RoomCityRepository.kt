package com.example.weather.repository.city

import com.example.weather.repository.city.room.CitiesListDao
import com.example.weather.repository.city.room.entities.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.*

typealias CityListener = (cities: List<City>) -> Unit

class RoomCityRepository(
    private val citiesListDao: CitiesListDao
): CityRepository {

    private var cities = mutableListOf<City>()
    private val listeners = mutableSetOf<CityListener>()

    init {
        cities = (0..1).map {
            City(
                id = it,
                name = cityName[it],
                description = cityDescriptions[it],
                mLatitudeTextView = 0.0,
                mLongitudeTextView = 0.0
            )
        }.toMutableList()
    }

    override suspend fun moveCity(city: City, moveBy: Int){
        val oldIndex = cities.indexOfFirst { it.id == city.id }
        //if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        //if (newIndex < 0 || newIndex >= cities.size) return
        delay(100)
        Collections.swap(cities, oldIndex, newIndex)
        notifyChanges()
    }

    override suspend fun getAvailableCity(): List<City> = withContext(Dispatchers.IO) {
        delay(100)
        return@withContext cities
    }

    override fun deleteCity(city: City): Flow<Int> = flow{
        val delIndex = cities.indexOfFirst { it.id == city.id }
        var process = 0
        while (process< 100){
            process += 5
            delay(100)
            emit(process)
        }
        cities.removeAt(delIndex)
        notifyChanges()
    }

    override fun listenerCurrentListCities(): Flow<List<City>> = callbackFlow{
        val listener:CityListener = {
            trySend(it)
        }
        listeners.add(listener)
        awaitClose{
            listeners.remove(listener)
        }
    }.buffer(Channel.CONFLATED)

    private fun notifyChanges() {
        listeners.forEach { it.invoke(cities) }
    }

    companion object{
        private val cityName = mutableListOf("moscow", "saint%20petersburg")
        private val cityDescriptions = mutableListOf("Москва", "Санкт-Петербург")
    }
}