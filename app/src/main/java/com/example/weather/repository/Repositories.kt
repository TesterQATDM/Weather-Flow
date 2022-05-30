package com.example.weather.repository

import android.content.Context
import androidx.room.Room
import com.example.weather.repository.account.RoomAccountsRepository
import com.example.weather.repository.account.AccountRepository
import com.example.weather.repository.city.CityInMemory
import com.example.weather.repository.city.CityRepository
import com.example.weather.repository.roomdatabase.AppDatabase
import com.example.weather.repository.settings.AppSettings
import com.example.weather.repository.settings.SharedPreferencesAppSettings

object Repositories {

    private lateinit var applicationContext: Context

    val cityRepository: CityRepository = CityInMemory()

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(applicationContext)
    }

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database300520221506.db")
            .build()
    }

    val accountsRepository: AccountRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao(), appSettings)
    }

    fun init(context: Context) {
        applicationContext = context
    }
}