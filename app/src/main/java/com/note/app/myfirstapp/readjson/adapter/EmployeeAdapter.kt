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
import com.note.app.myfirstapp.readjson.models.Employee
import com.note.app.myfirstapp.readjson.models.ModelClassJsonItem
import java.util.*

class EmployeeAdapter(val context: Context, private val list: List<Employee> ) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(p0.context)
                .inflate(R.layout.employee_json_design, p0, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.tvEmployeeName.text = data.name
        holder.tvEmployeeAge.text = data.age
        holder.tvEmployeeCity.text = data.city

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEmployeeName: TextView = view.findViewById(R.id.tvEmployeeName)
        val tvEmployeeAge: TextView = view.findViewById(R.id.tvEmployeeAge)
        val tvEmployeeCity: TextView = view.findViewById(R.id.tvEmployeeCity)

    }
}