package com.note.app.myfirstapp.readjson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.note.app.myfirstapp.R
import com.note.app.myfirstapp.readjson.adapter.EmployeeAdapter
import com.note.app.myfirstapp.readjson.adapter.JsonAdapter
import com.note.app.myfirstapp.readjson.models.Employee
import com.note.app.myfirstapp.readjson.models.ModelClassJsonItem
import kotlinx.android.synthetic.main.activity_read_json.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class CountryReadJsonActivity : AppCompatActivity() {

    private lateinit var jsonAdapter: JsonAdapter
    private val list = ArrayList<ModelClassJsonItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_json)

        readJsonFileCountry()
    }

    private fun readJsonFileCountry() {
        var json: String?
        try {
            val inputStream: InputStream = assets.open("country_std_code.json")
            json = inputStream.bufferedReader().use { it.readText() }
            var array = JSONArray(json)
            for (i in 0 until array.length()) {
                val objInside = JSONObject(array.get(i).toString())
                val name = objInside.getString("name")
                val dialCode = objInside.getString("dialCode")
                val code = objInside.getString("code")
                list.add(ModelClassJsonItem(code, dialCode, name))
            }
            setRecyclerViewCountry(list)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setRecyclerViewCountry(arrayList: ArrayList<ModelClassJsonItem>) {

        jsonAdapter = JsonAdapter(this, arrayList)
        recyclerJason.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                this@CountryReadJsonActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = jsonAdapter
        }
    }
}