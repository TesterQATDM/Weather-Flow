package com.example.weather.repository.city.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesListDao {

    @Insert(entity = CitiesListDbEntity::class)
    suspend fun createCity(cityListDbEntity: CitiesListDbEntity)

    @Query("SELECT cityName FROM citiesListTable")
    fun citiesList(): Flow<List<String>>

}