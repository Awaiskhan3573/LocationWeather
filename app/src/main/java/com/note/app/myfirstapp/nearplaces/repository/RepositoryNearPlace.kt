package com.note.app.myfirstapp.nearplaces.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.note.app.myfirstapp.nearplaces.api.NearPlacesInterface
import com.note.app.myfirstapp.nearplaces.models.NearPlaceModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryNearPlace(private val nearPlacesInterface: NearPlacesInterface) {
    companion object{
         private const val clientId ="4P0LUACTHVE1KHGX3L5UYNYZ1CZ5XPU1QM0W5WMQ4BUO0WOU"
         private const val clientSecret ="ASCOND0QVBRCDKGG3LXS1JEQWDFCUGZX40DUARGJLZVRHMSP"
         private const val v ="20201114"
         private const val radius ="10000"
         private const val limit  ="30"
         private const val auth ="fsq3KSpuRyKKJwy1KNs2YVQUkUq5AzHeM43nwG9X3RC6lPg="

    }
    private val nearPlaceLiveData=MutableLiveData<NearPlaceModel>()
    val placeList : LiveData<NearPlaceModel>
    get() = nearPlaceLiveData


    fun getNearPlaces(ll:String){
        val placeDetail=nearPlacesInterface.getNearPlaces(clientId, clientSecret,v, radius, ll,limit, auth)
        placeDetail.enqueue(object :Callback<NearPlaceModel>{
            override fun onResponse(
                call: Call<NearPlaceModel>,
                response: Response<NearPlaceModel>
            ) {

                if (response.isSuccessful) {
                    nearPlaceLiveData.postValue(response.body())
                }else{
                    Log.d("PLACE","response.body()!!${response.message()}")
                }

            }

            override fun onFailure(call: Call<NearPlaceModel>, t: Throwable) {
                Log.d("PLACE","response.body()!!${t.message}")
            }

        })
    }

}