package com.note.app.myfirstapp.weatherforecast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.note.app.myfirstapp.R
import com.note.app.myfirstapp.helper.WeatherHelper
import com.note.app.myfirstapp.weatherforecast.models.WeatherList

class WeatherAdapter(
    val context: Context,
    private val list: ArrayList<WeatherList> ,
    private val callback: WeatherCallback
) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(p0.context)
                .inflate(R.layout.weather_design_item, p0, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.tvDegree.text = (data.main.temp - 273).toInt().toString()
        holder.tvDate.text = WeatherHelper.splitString(data.dt_txt,0)
        holder.tvTime.text=WeatherHelper.splitString(data.dt_txt,1)
        Glide.with(context).load("http://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png").into(holder.imgStatus)
        holder.itemView.setOnClickListener{
           callback.onItemClick(data)
        }
    }

    override fun getItemCount(): Int {
        return list.size

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDegree: TextView = view.findViewById(R.id.tvDegree)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val imgStatus: ImageView = view.findViewById(R.id.imgStatus)
        val tvTime :TextView  = view.findViewById(R.id.tvTime)


    }
}