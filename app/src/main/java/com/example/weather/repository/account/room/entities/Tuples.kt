package com.example.weather.repository.account.room.entities

import androidx.room.ColumnInfo

data class AccountSignInTuple(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "password") val password: String
)

data class AccountUpdateUsernameTuple(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "username") val username: String
)

data class CityDeleteTuple(
    @ColumnInfo(name = "cityName") val cityName: String
)