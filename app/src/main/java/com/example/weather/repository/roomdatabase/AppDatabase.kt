package com.example.weather.repository.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.repository.account.room.AccountDbEntity
import com.example.weather.repository.account.room.AccountsDao
import com.example.weather.repository.city.room.CitiesListDao
import com.example.weather.repository.city.room.CitiesListDbEntity

@Database(
    version = 1,
    entities = [
        AccountDbEntity::class,
        CitiesListDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getCitiesListDao(): CitiesListDao

}