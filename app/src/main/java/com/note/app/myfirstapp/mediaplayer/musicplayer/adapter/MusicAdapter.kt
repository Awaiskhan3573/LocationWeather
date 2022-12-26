package com.note.app.myfirstapp.mediaplayer.musicplayer.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.note.app.myfirstapp.R
import com.note.app.myfirstapp.mediaplayer.musicplayer.model.MusicModel

class MusicAdapter(
    val context: Context, private val list:List<MusicModel> ) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(p0.context)
                .inflate(R.layout.music_layout_design, p0, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        if (data.isPlaying)
        {
            holder.rootCL.setBackgroundResource(R.drawable.music_shape)
        }else{
            holder.rootCL.setBackgroundResource(R.drawable.music_shape2)
        }

    }

    override fun getItemCount(): Int {
        return list.size

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvArtists: TextView = view.findViewById(R.id.tvArtist)
        val tvDuration: TextView = view.findViewById(R.id.tvDuration)
        val rootCL: TextView = view.findViewById(R.id.rootCL)




    }
}