package com.example.weather.di

import android.util.Log
import com.example.weather.utils.logger.LogCatLogger
import com.example.weather.utils.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module for providing [Logger] implementation based on
 * system [Log] class.
 */
@Module
@InstallIn(SingletonComponent::class)
class StuffsModule {

    /**
     * We don't need scope annotation here because LogCatHolder is
     * 'object' (already singleton)
     */
    @Provides
    fun provideLogger(): Logger {
        return LogCatLogger
    }
}