package com.note.app.myfirstapp.nearplaces.api

import com.note.app.myfirstapp.weatherforecast.api.RetrofitHelperForecast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelperNearPlace {

   private const val BASE_URL="https://api.foursquare.com/v3/"



    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}