package com.note.app.myfirstapp.weatherforecast

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.note.app.myfirstapp.databinding.ActivityForcastWeatherBinding
import com.note.app.myfirstapp.helper.WeatherHelper
import com.note.app.myfirstapp.location.LocationRepository
import com.note.app.myfirstapp.location.callBack.CurrentLocation
import com.note.app.myfirstapp.weatherforecast.adapter.WeatherAdapter
import com.note.app.myfirstapp.weatherforecast.adapter.WeatherCallback
import com.note.app.myfirstapp.weatherforecast.models.WeatherList
import com.note.app.myfirstapp.weatherforecast.viewmodel.MainViewModel

class ForecastWeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForcastWeatherBinding

    lateinit var mainViewModel: MainViewModel
    private lateinit var mWeatherAdapter: WeatherAdapter
    lateinit var locationRepository: LocationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForcastWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrentLocation()



        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.weatherList.observe(this, Observer {
            Log.d("awais", it.city.name.toString())

            setRecyclerView(it.list as ArrayList<WeatherList>)

            binding.tvDegree.text = (it.list[0].main.temp - 273.15).toInt().toString()
            binding.tvRain.text = it.list[0].weather[0].description
            binding.tvDate.text = WeatherHelper.splitString(it.list[0].dt_txt, 0)
            Glide.with(this)
                .load("http://openweathermap.org/img/wn/${it.list[0].weather[0].icon}@2x.png")
                .into(binding.imgStatus)
        })
        binding.weatherToolbar.backPress.setOnClickListener {
            onBackPressed()
        }
    }
    private fun setRecyclerView(arrayList: ArrayList<WeatherList>) {

        mWeatherAdapter = WeatherAdapter(this, arrayList, object : WeatherCallback {
            override fun onItemClick(model: WeatherList) {
                weatherDetails(model)
            }
        })
        binding.weatherRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                this@ForecastWeatherActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = mWeatherAdapter
        }
    }
    private fun getCurrentLocation(){
        locationRepository= LocationRepository(this,object :CurrentLocation{
            override fun onLocationView(location: Location) {
                Log.d("onLocationView", "onLocationView: ====${location.latitude}=")
                val latitude = location.latitude.toString()
                val longitude=location.longitude.toString()


                mainViewModel.getForecastWeather(latitude,longitude)
               locationRepository.stopLocation()
            }

        })
        locationRepository.startLocation()
    }
    private fun weatherDetails(model: WeatherList) {
        binding.tvPercentHumidity.text = model.main.humidity.toString()
        binding.tvPercentPressure.text = model.main.pressure.toString()
        binding.tvPercentWind.text = model.wind.speed.toString()
        binding.tvPercentWindPressure.text = model.wind.gust.toString()
        binding.tvPercentCloud.text = model.clouds.all.toString()
        binding.tvPercent.text = model.main.temp_kf.toString()
    }
}