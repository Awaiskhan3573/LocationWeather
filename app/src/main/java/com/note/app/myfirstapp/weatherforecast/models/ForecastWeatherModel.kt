package com.note.app.myfirstapp.weatherforecast.models

data class ForecastWeatherModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherList>,
    val message: Int
)