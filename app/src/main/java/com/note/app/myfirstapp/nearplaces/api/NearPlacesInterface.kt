package com.note.app.myfirstapp.nearplaces.api

import com.note.app.myfirstapp.nearplaces.models.NearPlaceModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NearPlacesInterface {
    @GET("places/search?")
    fun getNearPlaces(
        @Query("client_id") clientId:String,
        @Query("client_secret") clientSecret:String,
        @Query("v") v:String,
        @Query("radius") radius:String,
        @Query("ll") ll:String,
        @Query("limit") limit :String,
        @Header("Authorization") auth:String):Call<NearPlaceModel>

}