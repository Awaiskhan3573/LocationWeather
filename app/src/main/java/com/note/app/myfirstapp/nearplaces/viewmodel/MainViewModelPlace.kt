package com.note.app.myfirstapp.nearplaces.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.note.app.myfirstapp.nearplaces.api.NearPlacesInterface
import com.note.app.myfirstapp.nearplaces.api.RetrofitHelperNearPlace
import com.note.app.myfirstapp.nearplaces.models.NearPlaceModel
import com.note.app.myfirstapp.nearplaces.repository.RepositoryNearPlace

class MainViewModelPlace():ViewModel() {
    val nearPlacesInterface= RetrofitHelperNearPlace.getInstance().create(NearPlacesInterface::class.java)
    val repositoryNearPlace=RepositoryNearPlace(nearPlacesInterface)


    fun getLocationNearMe(ll:String) {
       this.repositoryNearPlace.getNearPlaces(ll)
    }
    val placeList:LiveData<NearPlaceModel>
    get() = repositoryNearPlace.placeList
}