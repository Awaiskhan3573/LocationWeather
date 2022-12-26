package com.note.app.myfirstapp.location.callBack

import com.mapbox.common.location.Location
import com.mapbox.geojson.Point

interface SearchLocation {
    fun getSearchLocation(point: Point,placeName:String)
}