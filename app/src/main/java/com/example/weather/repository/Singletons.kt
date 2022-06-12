package com.example.weather.repository

import android.content.Context
import androidx.room.Room
import com.example.weather.moshiAndRetrofit.SourceProviderHolder
import com.example.weather.moshiAndRetrofit.base.SourcesProvider
import com.example.weather.repository.account.RoomAccountsRepository
import com.example.weather.repository.account.AccountRepository
import com.example.weather.repository.city.RoomCityRepository
import com.example.weather.repository.city.CityRepository
import com.example.weather.repository.roomdatabase.AppDatabase
import com.example.weather.repository.settings.AppSettings
import com.example.weather.repository.settings.SharedPreferencesAppSettings
import com.example.weather.repository.weather.WeatherSource
import com.example.weather.repository.weather.moshiAndWeatherApi.RetrofitWeatherRepository

object Singletons {

    private lateinit var appContext: Context

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(appContext, AppDatabase::class.java, "database300520221506.db")
            .build()
    }

    val accountsRepository: AccountRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao(), appSettings)
    }

    val cityRepository: CityRepository by lazy {
        RoomCityRepository(database.getCitiesListDao())
    }

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }

    // --- sources

    private val retrofitWeatherSource: WeatherSource by lazy {
        sourcesProvider.getWeatherRepository()
    }

    // --- repositories

    val retrofitWeatherRepository: RetrofitWeatherRepository by lazy {
        RetrofitWeatherRepository(
            weatherSource = retrofitWeatherSource
        )
    }

    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}