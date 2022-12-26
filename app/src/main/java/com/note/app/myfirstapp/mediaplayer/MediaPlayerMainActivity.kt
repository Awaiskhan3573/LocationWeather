package com.note.app.myfirstapp.mediaplayer
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.note.app.myfirstapp.R
import kotlinx.android.synthetic.main.activity_media_player_main.*


class MediaPlayerMainActivity : AppCompatActivity() {


     private lateinit var mp:MediaPlayer

    private val uri= "https://www.kozco.com/tech/32.mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player_main)
        mp=MediaPlayer()
        btnPlay.setOnClickListener {
            play()
        }

        btnPause.setOnClickListener {
            pause()
        }
    }
     fun play() {
      mp.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            // on below line we are setting audio
            // source as audio url on below line.
           mp.setDataSource(uri)
            mp.prepareAsync()
            mp.setOnPreparedListener {
                mp.start()
            }

        } catch (e: Exception) {

            // on below line we are handling our exception.
            e.printStackTrace()
        }
        // on below line we are displaying a toast message as audio player.
        Toast.makeText(applicationContext, "Audio started playing..", Toast.LENGTH_SHORT).show()


    }
   fun pause() {
        if (mp.isPlaying) {
            // if media player is playing we
            // are stopping it on below line.
            mp.stop()

            // on below line we are resetting
            // our media player.
            mp.reset()

            // on below line we are calling
            // release to release our media player.


            // on below line we are displaying a toast message to pause audio/
            Toast.makeText(applicationContext, "Audio has been  paused..", Toast.LENGTH_SHORT)
                .show()

        } else {
            // if audio player is not displaying we are displaying below toast message
            Toast.makeText(applicationContext, "Audio not played..", Toast.LENGTH_SHORT).show()
        }

    }
}


/*
    fun onPrepared(mp: mp) {
        Log.d(TAG, "Stream is prepared")
        mp.start()
    }*/



   /* override fun onDestroy() {
        super.onDestroy()
        stop()
    }

    fun onCompletion(mp: mp?) {
        stop()
    }*/





