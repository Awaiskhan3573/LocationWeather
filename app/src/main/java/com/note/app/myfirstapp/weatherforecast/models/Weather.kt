package com.note.app.myfirstapp.weatherforecast.models

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)