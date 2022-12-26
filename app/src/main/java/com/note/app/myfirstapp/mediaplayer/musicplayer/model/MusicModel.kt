package com.note.app.myfirstapp.mediaplayer.musicplayer.model

import android.net.Uri

data class MusicModel (
     val title:String,
     val artist:String,
     val  duration:String,
     val isPlaying:Boolean,
     val musicFile:Uri
)

