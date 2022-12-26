package com.note.app.myfirstapp.weatherforecast.api

import com.note.app.myfirstapp.weatherforecast.models.ForecastWeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastWeatherInterface {
    @GET("forecast?")
    fun getForecastWeather(@Query("lat")latitude:String,@Query("lon")longitude:String,@Query("appId")apikey:String):Call<ForecastWeatherModel>



}
