package com.example.weather.di

import android.util.Log
import com.example.weather.moshiAndRetrofit.const.Const
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * This module provides instances for library network classes:
 * - [Moshi]
 * - [OkHttpClient]
 * - [Retrofit]
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        val r = Retrofit.Builder()
            .baseUrl(Const.BASE_URL_WEATHER_API)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

        Log.e("ПРОВЕРКА", r.toString())
        return r
    }
}