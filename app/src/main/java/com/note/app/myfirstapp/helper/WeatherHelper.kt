package com.note.app.myfirstapp.helper

class WeatherHelper {
    companion object{


        fun splitString(data:String,type:Int):String{
            val delimiter = " "

            val parts = data.split(delimiter)
            if (type==0){
                return parts[0]
            }else{
                return parts[1]
            }
        }



    }
}