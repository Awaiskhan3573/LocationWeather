package com.note.app.myfirstapp.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.note.app.myfirstapp.weatherforecast.api.ForecastWeatherInterface
import com.note.app.myfirstapp.weatherforecast.api.RetrofitHelperForecast
import com.note.app.myfirstapp.weatherforecast.models.ForecastWeatherModel
import com.note.app.myfirstapp.weatherforecast.repository.RepositoryForecast

class MainViewModel:ViewModel() {

    val forecastWeatherInterface=RetrofitHelperForecast.getInstance().create(ForecastWeatherInterface::class.java)
    val repositoryForecast =RepositoryForecast(forecastWeatherInterface)
    fun getForecastWeather(latitude:String,longitude:String) {
        this.repositoryForecast.getForecastWeather(latitude,longitude)

    }
    val weatherList:LiveData<ForecastWeatherModel>
    get() = repositoryForecast.weatherList

}
