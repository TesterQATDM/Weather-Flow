package com.example.weather.di

import com.example.weather.moshiAndRetrofit.RetrofitWeatherSource
import com.example.weather.repository.account.AccountRepository
import com.example.weather.repository.account.RoomAccountsRepository
import com.example.weather.repository.city.CityRepository
import com.example.weather.repository.city.RoomCityRepository
import com.example.weather.repository.weather.WeatherSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module binds concrete sources implementations to their
 * interfaces: [RetrofitAccountsSource] is bound to [AccountsSource]
 * and [RetrofitBoxesSource] is bound to [BoxesSource].
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {

    @Binds
    abstract fun bindAccountsSource(
        roomAccountsRepository: RoomAccountsRepository
    ): AccountRepository

    @Binds
    abstract fun bindCitySource(
        roomCityRepository: RoomCityRepository
    ): CityRepository

    @Binds
    abstract fun bindWeatherSource(
        retrofitBoxesSource: RetrofitWeatherSource
    ): WeatherSource
}