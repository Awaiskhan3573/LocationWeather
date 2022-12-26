package com.note.app.myfirstapp.readjson.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.note.app.myfirstapp.R
import com.note.app.myfirstapp.readjson.models.ModelClassJsonItem
import java.util.*

class JsonAdapter(val context: Context, private val list: List<ModelClassJsonItem> ) : RecyclerView.Adapter<JsonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(p0.context)
                .inflate(R.layout.json_value_design, p0, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.tvName.text = data.name
        holder.tvName.setOnClickListener {
            Toast.makeText(context, " country name", Toast.LENGTH_SHORT).show()
        }
        holder.tvCode.text = data.code
        holder.tvCode.setOnClickListener {
            Toast.makeText(context, " short name", Toast.LENGTH_SHORT).show()
        }

        holder.tvCountryCode.text = data.dialCode
        holder.tvCountryCode.setOnClickListener {
            Toast.makeText(context, "dial code", Toast.LENGTH_SHORT).show()
        }
        Glide.with(context).load(
            "https://flagpedia.net/data/flags/normal/${
                data.code.lowercase(
                    Locale.getDefault()
                )
            }.png"
        ).into(holder.flag)
        holder.flag.setOnClickListener {
            Toast.makeText(context, "${data.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvCode: TextView = view.findViewById(R.id.tvCode)
        val tvCountryCode: TextView = view.findViewById(R.id.tvCountryCode)
        val flag: ImageView = view.findViewById(R.id.flag)
    }
}