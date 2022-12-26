package com.note.app.myfirstapp.mediaplayer.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.note.app.myfirstapp.R
import com.note.app.myfirstapp.databinding.ActivityMediaPlayerMainBinding
import com.note.app.myfirstapp.databinding.ActivityMusicPlayerBinding
import com.note.app.myfirstapp.mediaplayer.musicplayer.adapter.MusicAdapter
import com.note.app.myfirstapp.mediaplayer.musicplayer.model.MusicModel
import com.note.app.myfirstapp.weatherforecast.adapter.WeatherAdapter
import com.note.app.myfirstapp.weatherforecast.adapter.WeatherCallback
import com.note.app.myfirstapp.weatherforecast.models.WeatherList

class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMusicPlayerBinding
    private lateinit var mMusicAdapter: MusicAdapter
     private val list= ArrayList<MusicModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView(list)
    }
    private fun setRecyclerView(arrayList: ArrayList<MusicModel>) {

        mMusicAdapter = MusicAdapter(this,arrayList)
        binding.recyclerMusic.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                this@MusicPlayerActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = mMusicAdapter
        }
    }
}