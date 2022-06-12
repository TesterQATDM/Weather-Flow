package com.example.weather.moshiAndRetrofit.entities

data class Condition(
    var text: String,
    var icon: String
)

data class Current(
    var temp_c: Double,
    var condition: Condition,
    var uv: Double,
)

data class Location(
    var name: String,
    var region: String,
    var country: String,
    var lat: Double,
    var lon: Double,
    var tz_id: String,
    var localtime_epoch: Int,
    var localtime: String,
)

data class WeatherApiForm(
    var location: Location,
    var current: Current
)