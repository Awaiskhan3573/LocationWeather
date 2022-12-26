package com.note.app.myfirstapp.readjson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.note.app.myfirstapp.R
import com.note.app.myfirstapp.readjson.adapter.EmployeeAdapter
import com.note.app.myfirstapp.readjson.models.Employee
import kotlinx.android.synthetic.main.activity_employee_read_json.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class EmployeeReadJsonActivity : AppCompatActivity() {

    private lateinit var employeeAdapter: EmployeeAdapter
    private val employeeList=ArrayList<Employee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_read_json)

        readJsonFileEmployee()
    }
    private fun readJsonFileEmployee(){
        var json:String?
        try {
            val inputStream:InputStream=assets.open("employee.json")
            json=inputStream.bufferedReader().use {it.readText() }
           var arrayEmployee=JSONArray(json)
            for (i in 0 until arrayEmployee.length())
            {
                val objJson = JSONObject(arrayEmployee.get(i).toString())
                val name=objJson.getString("name")
                val age=objJson.getString("age")
                val city=objJson.getString("city")
                employeeList.add(Employee(age,city,name))
            }
            setRecyclerViewEmployee(employeeList)

        }catch (e:IOException)
        {
            e.printStackTrace()
        }
    }
    private fun setRecyclerViewEmployee(arrayList: ArrayList<Employee>){
       employeeAdapter= EmployeeAdapter(this,arrayList)
        employeeRecycler.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(this@EmployeeReadJsonActivity,LinearLayoutManager.VERTICAL,false)
            adapter=employeeAdapter
        }
    }
}