package com.example.weather.repository.city.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.repository.account.room.entities.CityDeleteTuple

@Dao
interface CitiesListDao {

    @Insert(entity = CitiesListDbEntity::class)
    suspend fun createCity(cityListDbEntity: CitiesListDbEntity)

    @Query("SELECT cityName FROM citiesListTable")
    fun citiesList(): List<String>

    @Delete(entity = CitiesListDbEntity::class)
    fun delete(cityDeleteTuple: CityDeleteTuple)

}