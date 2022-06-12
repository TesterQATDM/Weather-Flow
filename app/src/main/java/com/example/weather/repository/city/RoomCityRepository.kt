package com.example.weather.repository.city

import android.database.sqlite.SQLiteConstraintException
import com.example.weather.repository.account.room.entities.CityDeleteTuple
import com.example.weather.repository.city.room.CitiesListDao
import com.example.weather.repository.city.room.CitiesListDbEntity
import com.example.weather.utils.CityIsExistException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

typealias CityListener = (cities: List<String>) -> Unit

class RoomCityRepository(
    private val citiesListDao: CitiesListDao
): CityRepository {

    private var citiesRoom = mutableListOf<String>()

    private val listeners = mutableSetOf<CityListener>()

    override suspend fun getAvailableCityRoom(): List<String>  = withContext(Dispatchers.IO) {
        citiesRoom = citiesListDao.citiesList().toMutableList()
        return@withContext citiesRoom
    }

    override suspend fun deleteCityRoom(cityName: String) = withContext(Dispatchers.IO){
        val tempCityDelete = CityDeleteTuple(cityName)
        citiesListDao.delete(tempCityDelete)
        citiesRoom = citiesListDao.citiesList().toMutableList()
        notifyChanges()
        return@withContext
    }

    override suspend fun addCity(cityName: String) = withContext(Dispatchers.IO) {
        try {
            val entity = CitiesListDbEntity.fromCityListFragment(cityName)
            citiesListDao.createCity(entity)
            citiesRoom = citiesListDao.citiesList().toMutableList()
            notifyChanges()
        } catch (e: SQLiteConstraintException) {
            val appException = CityIsExistException()
            appException.initCause(e)
            throw appException
        }
        return@withContext
    }

    override fun listenerCurrentListCities(): Flow<List<String>> = callbackFlow{
        val listener:CityListener = {
            trySend(it)
        }
        listeners.add(listener)
        awaitClose{
            listeners.remove(listener)
        }
    }.buffer(Channel.CONFLATED)

    private fun notifyChanges() {
        listeners.forEach { it.invoke(citiesRoom) }
    }
}
