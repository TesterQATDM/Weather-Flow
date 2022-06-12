package com.example.weather.repository.city.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "citiesListTable",
    indices = [
        Index("cityName", unique = true)
    ]
)

data class CitiesListDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "cityName", collate = ColumnInfo.NOCASE) val cityName: String
) {
    companion object {
        fun fromCityListFragment(string: String) = CitiesListDbEntity(
            id = 0, // SQLite generates identifier automatically if ID = 0
            cityName = string
        )
    }
}