package com.example.weather.repository.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.repository.account.room.AccountDbEntity
import com.example.weather.repository.account.room.AccountsDao

@Database(
    version = 2,
    entities = [
        AccountDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    //abstract fun getCitiesListDao(): CitiesListDao

}