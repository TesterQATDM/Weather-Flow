package com.example.weather.di

import android.app.Application
import android.content.Context
import com.example.weather.repository.account.room.AccountsDao
import com.example.weather.repository.city.room.CitiesListDao
import com.example.weather.repository.roomdatabase.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule{

    @Singleton
    @Provides
    fun getDB(context: Application): AppDatabase{
        return AppDatabase.getDB(context)
    }

    @Singleton
    @Provides
    fun getAccountsDao(appDB: AppDatabase): AccountsDao{
        return appDB.getAccountsDao()
    }

    @Singleton
    @Provides
    fun getCitiesListDao(appDB: AppDatabase): CitiesListDao {
        return appDB.getCitiesListDao()
    }
}