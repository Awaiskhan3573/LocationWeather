package com.note.app.myfirstapp.location.callBack

import android.location.Location

interface CurrentLocation {
    fun onLocationView(location: Location)
}