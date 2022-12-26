package com.note.app.myfirstapp.nearplaces

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.note.app.myfirstapp.R
import com.note.app.myfirstapp.databinding.ActivityNearPlacesMainBinding
import com.note.app.myfirstapp.helper.AppUtils
import com.note.app.myfirstapp.location.LocationRepository
import com.note.app.myfirstapp.location.callBack.CurrentLocation
import com.note.app.myfirstapp.nearplaces.api.NearPlacesInterface
import com.note.app.myfirstapp.nearplaces.api.RetrofitHelperNearPlace
import com.note.app.myfirstapp.nearplaces.repository.RepositoryNearPlace
import com.note.app.myfirstapp.nearplaces.viewmodel.MainViewModelPlace

class NearPlacesMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNearPlacesMainBinding
    private var mapView: MapView? = null
    private lateinit var mapboxMap: MapboxMap
    private val zoom = 16.0
    private lateinit var locationRepository: LocationRepository
    private lateinit var mainViewModelPlace: MainViewModelPlace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ResourceOptionsManager.getDefault(this, AppUtils.accessToken).update {
            tileStoreUsageMode(TileStoreUsageMode.READ_ONLY)
        }
        binding= ActivityNearPlacesMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapView = findViewById(R.id.mapviewNearPlace)
        mapboxMap = binding.mapviewNearPlace.getMapboxMap()
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS) {
            getCurrentLocation(mapView!!)

        }
        binding.toolbarNearPlace.backPress.setOnClickListener {
            onBackPressed()
        }
        binding.btnNearPlaces.setOnClickListener {
            nearByPlace()
        }

        mainViewModelPlace=ViewModelProvider(this)[MainViewModelPlace::class.java]


    }
    private fun nearByPlace()
    {

        mainViewModelPlace.placeList.observe(this, Observer {
            Log.d("NEARPLACE",it.results[0].location.address.toString())

            for (i in it.results.indices){
                addAnnotationToMapNearPlace(this,it.results[i].geocodes.main.latitude,it.results[i].geocodes.main.longitude,mapView!!)
            }

        })
    }
    private fun getCurrentLocation(mapView: MapView) {

        locationRepository = LocationRepository(this, object : CurrentLocation {
            override fun onLocationView(location: Location) {
//              Toast.makeText(this@LocationMainActivity,"${location.latitude } ${location.longitude}",Toast.LENGTH_SHORT).show()
                Log.d("onLocationView", "onLocationView: ====${location.latitude}=")
                locationRepository.stopLocation()

                val ll=location.latitude.toString()+","+location.longitude.toString()
                mainViewModelPlace.getLocationNearMe(ll)
                addAnnotationToMap(
                    this@NearPlacesMainActivity,
                    location.latitude,
                    location.longitude,
                    mapView
                )
            }
        })
        locationRepository.startLocation()
    }
    private fun addAnnotationToMapNearPlace(
        context: Context,
        latitude: Double,
        longitude: Double,
        mapView: MapView
    ) {
        convertDrawableToBitmapNearPlace(
            context
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude))
                .withIconImage(it)
            pointAnnotationManager.create(pointAnnotationOptions)

        }
    }
    private fun convertDrawableToBitmapNearPlace(context: Context): Bitmap? {

        val sourceDrawable = AppCompatResources.getDrawable(context, R.drawable.location_pin
        )

        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            val constantState = sourceDrawable?.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
    private fun addAnnotationToMap(
        context: Context,
        latitude: Double,
        longitude: Double,
        mapView: MapView
    ) {
        convertDrawableToBitmap(
            context
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude))
                .withIconImage(it)
            pointAnnotationManager.create(pointAnnotationOptions)
            cameraZoom(latitude, longitude, mapboxMap, zoom)
        }
    }

    private fun convertDrawableToBitmap(context: Context): Bitmap? {

        val sourceDrawable = AppCompatResources.getDrawable(context, R.drawable.new_location
        )

        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            val constantState = sourceDrawable?.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    private fun cameraZoom(
        latitude: Double,
        longitude: Double,
        mapboxMap: MapboxMap,
        zoom: Double
    ) {
        mapboxMap.flyTo(
            cameraOptions {
                center(
                    Point.fromLngLat(
                        longitude,
                        latitude
                    )
                ) // Sets the new camera position on click point
                zoom(zoom) // Sets the zoom
                bearing(270.0) // Rotate the camera
                pitch(50.0) // Set the camera pitch
            },
            MapAnimationOptions.mapAnimationOptions {
                duration(3000)
            }
        )
    }
    override fun onStart() {
        super.onStart()
        mapView!!.onStart()

    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

}