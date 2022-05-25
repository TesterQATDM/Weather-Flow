package com.example.weather.repository.city.room.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val id: Int,
    val name: String,
    val description: String,
    val mLatitudeTextView: Double,
    val mLongitudeTextView: Double
): Parcelable