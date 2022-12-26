package com.note.app.myfirstapp.weatherforecast.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelperForecast {

    const val BASE_URL="https://api.openweathermap.org/data/2.5/"


    fun getInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}