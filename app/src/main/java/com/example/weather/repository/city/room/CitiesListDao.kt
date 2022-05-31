package com.example.weather.repository.city.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.repository.account.room.AccountDbEntity

// todo #9: Create a DAO interface for querying data from 'accounts' table, updating username and
//          creating a new account.
//          - annotate it with @Dao annotation.
//          - add method for querying ID and Password by email:
//            - annotate the method with @Query
//            - write a SQL-query to fetch ID and Password by Email
//            - return a tuple created before (step #6)
//          - add method for updating username:
//            - use @Update annotation and specify 'entity' parameter = AccountDbEntity::class
//            - add one argument to pass a tuple created before (step #7)
//          - add method for creating a new account
//            - use @Insert annotation
//            - add one argument: AccountDbEntity
//          - add method for querying account info by ID
//            - annotations: @Query
//            - arguments: accountId
//            - return type: Flow<AccountDbEntity>

@Dao
interface CitiesListDao {

    @Query("SELECT cityName * FROM citiesListTable ORDER BY id")
    suspend fun getCitiesList(): List<String>

    @Insert(entity = CitiesListDbEntity::class)
    suspend fun createCity(cityDbEntity: CitiesListDbEntity)

    @Delete(entity = CitiesListDbEntity::class)
    suspend fun deleteCity(cityDbEntity: CitiesListDbEntity)
}