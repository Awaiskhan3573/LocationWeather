package com.note.app.myfirstapp.weatherforecast.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.note.app.myfirstapp.weatherforecast.api.ForecastWeatherInterface
import com.note.app.myfirstapp.weatherforecast.models.ForecastWeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryForecast(private val forecastWeatherInterface: ForecastWeatherInterface) {
    companion object{
        private const val appId="0c8d6ae8ba46c62f296fbf5d18f95eae"
    }
    private val forecastLiveData=MutableLiveData<ForecastWeatherModel>()
    val weatherList:LiveData<ForecastWeatherModel>
    get() = forecastLiveData

    fun getForecastWeather(latitude:String,longitude:String){
       val weatherDetails=forecastWeatherInterface.getForecastWeather(latitude,longitude, appId)


        weatherDetails.enqueue(object :Callback<ForecastWeatherModel>{
            override fun onResponse(
                call: Call<ForecastWeatherModel>,
                response: Response<ForecastWeatherModel>
            ) {
                if(response.isSuccessful){
                    forecastLiveData.postValue(response.body())

                }else{
                    Log.d("check " ,"=response.body()!!==${response.body()!!.city}")
                }

            }
            override fun onFailure(call: Call<ForecastWeatherModel>, t: Throwable) {
                Log.d("check","=response.body()!!==${t.message}")

            }
        })
    }
}