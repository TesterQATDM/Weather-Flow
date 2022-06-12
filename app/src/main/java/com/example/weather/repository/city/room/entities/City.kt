package com.example.weather.repository.city.room.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val name: String,
    val mLatitudeTextView: Double,
    val mLongitudeTextView: Double
): Parcelable