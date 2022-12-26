package com.note.app.myfirstapp.weatherforecast.adapter

import com.note.app.myfirstapp.weatherforecast.models.WeatherList

interface WeatherCallback {
    fun onItemClick(model: WeatherList)
}