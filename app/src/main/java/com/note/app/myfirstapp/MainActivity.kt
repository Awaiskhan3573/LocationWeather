package com.note.app.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.note.app.myfirstapp.databinding.ActivityMainBinding
import com.note.app.myfirstapp.location.LocationMainActivity
import com.note.app.myfirstapp.mediaplayer.MediaPlayerMainActivity
import com.note.app.myfirstapp.mediaplayer.musicplayer.MusicPlayerActivity
import com.note.app.myfirstapp.mediaplayer.VideoPlayActivity
import com.note.app.myfirstapp.nearplaces.NearPlacesMainActivity
import com.note.app.myfirstapp.readjson.CountryReadJsonActivity
import com.note.app.myfirstapp.readjson.EmployeeReadJsonActivity
import com.note.app.myfirstapp.weatherforecast.ForecastWeatherActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.location.setOnClickListener {
            val intent=Intent(this,LocationMainActivity::class.java)
            startActivity(intent)
        }
        binding.weather.setOnClickListener {
            val intent=Intent(this,ForecastWeatherActivity::class.java)
            startActivity(intent)
        }
        binding.json.setOnClickListener {
            val intent=Intent(this,CountryReadJsonActivity::class.java)
            startActivity(intent)
        }
        binding.jsonEmployee.setOnClickListener {
            val intent=Intent(this,EmployeeReadJsonActivity::class.java)
            startActivity(intent)
        }
        binding.btnNearPlaces.setOnClickListener {
            val intent=Intent(this,NearPlacesMainActivity::class.java)
            startActivity(intent)
        }
        binding.btnMediaPlayer.setOnClickListener {
            val intent=Intent(this,MediaPlayerMainActivity::class.java)
            startActivity(intent)
        }
        binding.btnVideoPlayer.setOnClickListener {
            val intent=Intent(this,VideoPlayActivity::class.java)
            startActivity(intent)
        }
        binding.btnMusicPlayer.setOnClickListener {
            val intent=Intent(this, MusicPlayerActivity::class.java)
            startActivity(intent)
        }
    }
}