package com.example.weather.repository.account.room.entities

import androidx.room.ColumnInfo

// todo #6: Create a tuple for fetching account id + account password.
//          Tuple classes should not be annotated with @Entity but their fields may be
//          annotated with @ColumnInfo.

// todo #7: Create a tuple for updating account username.
//          Such tuples should contain a primary key ('id') in order to notify Room which row you want to update
//          and fields to be updated ('username' is this case).

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