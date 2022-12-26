package com.note.app.myfirstapp.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.extensions.coordinates
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.*
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.MapboxNavigationProvider
import com.mapbox.navigation.core.directions.session.RoutesObserver
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions
import com.mapbox.navigation.ui.maps.route.line.model.RouteLine
import com.mapbox.navigation.ui.maps.route.line.model.toNavigationRouteLines
import com.note.app.myfirstapp.R
import com.note.app.myfirstapp.databinding.ActivityLocationMainBinding
import com.note.app.myfirstapp.helper.AppUtils
import com.note.app.myfirstapp.location.callBack.CurrentLocation
import com.note.app.myfirstapp.location.callBack.SearchLocation
import kotlinx.android.synthetic.main.json_value_design.*


class LocationMainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLocationMainBinding
    private var mapView: MapView? = null
    private lateinit var mapboxMap: MapboxMap
    private val zoom = 14.0
    private var latOrigin: Double=0.0
    private var lonOrigin: Double=0.0
    private var latDestination: Double=0.0
    private var lonDestination: Double=0.0
    private  var origin:Point ?= null
    private  var destination :Point ?= null
    private lateinit var locationRepository: LocationRepository
    private val check = false
    private val flag = false
    private lateinit var mapboxNavigation: MapboxNavigation
    private lateinit var list:ArrayList<Point>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ResourceOptionsManager.getDefault(this, AppUtils.accessToken).update {
            tileStoreUsageMode(TileStoreUsageMode.READ_ONLY)
        }
        binding = ActivityLocationMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapView = findViewById(R.id.mapView)
        mapboxMap = binding.mapView.getMapboxMap()
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS) {
           getCurrentLocation(mapView!!)

        }
        binding.btnCurrent.setOnClickListener {
            getCurrentLocation(mapView!!)
        }
        binding.toolbar.backPress.setOnClickListener {
            onBackPressed()
        }
        binding.btnNext.setOnClickListener {

                val intent=Intent(this,NavigationMapBox10Activity::class.java)
                intent.putExtra("latDestination",latDestination)
                intent.putExtra("lonDestination",lonDestination)
                intent.putExtra("latOrigin",latOrigin)
                intent.putExtra("lonOrigin",lonOrigin)
                startActivity(intent)

        }

        mapboxNavigation = if (MapboxNavigationProvider.isCreated()) {
            MapboxNavigationProvider.retrieve()
        } else {
            MapboxNavigationProvider.create(
                NavigationOptions.Builder(applicationContext)
                    .accessToken(getString(R.string.maptoken))
                    .build()
            )
        }
        list=ArrayList<Point>()



        binding.addBtn.setOnClickListener {
if (origin!=null && destination!=null){
    fetchRoute()
}


        }
        binding.imgCircle.setOnClickListener {

            val dialogBox = SearchPlacesDialogBox(this, object : SearchLocation {
                override fun getSearchLocation(point: Point, placeName: String) {
                    binding.etFindOrigin.text=placeName
                    addAnnotationToMapOrigin(this@LocationMainActivity,point,mapView!!)
                }
            })
            dialogBox.show()
        }
        binding.imgMarker.setOnClickListener {

            val dialogBox = SearchPlacesDialogBox(this, object : SearchLocation {
                override fun getSearchLocation(point: Point, placeName: String) {
                    binding.etFindDestination.text=placeName
                    addAnnotationToMapDestination(this@LocationMainActivity,point,mapView!!)
                }
            })
            dialogBox.show()
        }
        binding.etFindOrigin.setOnClickListener {
            val dialogBox = SearchPlacesDialogBox(this, object : SearchLocation {
                override fun getSearchLocation(point: Point, placeName: String) {
                    binding.etFindOrigin.text=placeName
                    latOrigin=point.latitude()
                    lonOrigin=point.longitude()
//                    point(point)
//                    list.add(0,Point.fromLngLat(lonOrigin,latOrigin))
                    origin= point
                    Log.d("lisdata","${list.size}")
                    addAnnotationToMapOrigin(this@LocationMainActivity,point,mapView!!)

                }
            })
            dialogBox.show()

        }

        binding.etFindDestination.setOnClickListener {
            val dialogBox = SearchPlacesDialogBox(this, object : SearchLocation {
                override fun getSearchLocation(point: Point, placeName: String) {
                    binding.etFindDestination.text=placeName
                    latDestination=point.latitude()
                    lonDestination=point.longitude()
//                    point(point)
//                    list.add(1,Point.fromLngLat(lonDestination,latDestination))
                    destination= point
                    addAnnotationToMapDestination(this@LocationMainActivity,point,mapView!!)

                }
            })
            dialogBox.show()

        }
    }

    private fun getCurrentLocation(mapView: MapView) {

        locationRepository = LocationRepository(this, object : CurrentLocation {
            override fun onLocationView(location: Location) {
//              Toast.makeText(this@LocationMainActivity,"${location.latitude } ${location.longitude}",Toast.LENGTH_SHORT).show()
                Log.d("onLocationView", "onLocationView: ====${location.latitude}=")
                locationRepository.stopLocation()

                addAnnotationToMap(
                    this@LocationMainActivity,
                    location.latitude,
                    location.longitude,
                    mapView
                )
            }
        })
        locationRepository.startLocation()
    }
    private fun fetchRoute() {

        if (mapboxNavigation.getNavigationRoutes().isNotEmpty()) {
            mapboxNavigation.setNavigationRoutes(emptyList())
        }
        val routeOptions = RouteOptions.builder().applyDefaultNavigationOptions()
            .coordinatesList(listOf(origin,destination)).alternatives(true).build()
        mapboxNavigation.requestRoutes(routeOptions, object : NavigationRouterCallback {
            override fun onCanceled(routeOptions: RouteOptions, routerOrigin: RouterOrigin) {
                Toast.makeText(
                    this@LocationMainActivity,
                    "Route Fetched Canceled",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onFailure(reasons: List<RouterFailure>, routeOptions: RouteOptions) {
                Toast.makeText(this@LocationMainActivity, "Route Fetching Failed", Toast.LENGTH_SHORT)
                    .show()
//                Log.e("TAG", "onFailure: $reasons")
            }

            override fun onRoutesReady(routes: List<NavigationRoute>, routerOrigin: RouterOrigin) {
                if (routes.isNotEmpty()) {
                    mapboxNavigation.setNavigationRoutes(routes)
//                    cameraZoom(points[0].latitude(), points[0].longitude(), mapboxMap, 5.0)
//                    animateRoute(points)
                }
                initNavigation()
            }
        })

    }

    //    /    draw route observer
    private val routesObserver = RoutesObserver { routeUpdateResult ->
        if (routeUpdateResult.navigationRoutes.toDirectionsRoutes().isNotEmpty()) {
            // generate route geometries and render them
            val routeLines =
                routeUpdateResult.navigationRoutes.toDirectionsRoutes().map { RouteLine(it, null) }
            routeLineApi.setNavigationRouteLines(
                routeLines
                    .toNavigationRouteLines()
            ) { value ->
                binding.mapView.getMapboxMap().getStyle()?.apply {
                    routeLineView.renderRouteDrawData(this, value)

                }
            }
        } else {
            // remove the route line and route arrow from the map
            val style = binding.mapView.getMapboxMap().getStyle()
            if (style != null) {
                routeLineApi.clearRouteLine { value ->
                    routeLineView.renderClearRouteLineValue(
                        style,
                        value
                    )
                }
            }
        }
    }

    private val mapboxRouteLineOptions: MapboxRouteLineOptions by lazy {
        MapboxRouteLineOptions.Builder(this).withVanishingRouteLineEnabled(true)
            .withRouteLineBelowLayerId("road-label").build()
    }

    private val routeLineView: MapboxRouteLineView by lazy {
        MapboxRouteLineView(mapboxRouteLineOptions)
    }

    private val routeLineApi: MapboxRouteLineApi by lazy {
        MapboxRouteLineApi(mapboxRouteLineOptions)
    }


    @SuppressLint("MissingPermission")
    private fun initNavigation() {
        mapboxNavigation.apply {
            registerRoutesObserver(routesObserver)
        }
    }


    private fun addAnnotationToMapOrigin(
        context: Context,
        point: Point,
        mapView: MapView
    ) {
        convertDrawableToBitmapOrigin(
            context
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(it)
            pointAnnotationManager.create(pointAnnotationOptions)
            cameraZoom(point, mapboxMap, zoom)
        }
    }
    private fun addAnnotationToMapDestination(
        context: Context,
        point: Point,
        mapView: MapView
    ) {
        convertDrawableToBitmapOrigin(
            context
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(it)
            pointAnnotationManager.create(pointAnnotationOptions)
            cameraZoom(point, mapboxMap, zoom)
        }
    }

    private fun convertDrawableToBitmapOrigin(context: Context): Bitmap? {
        val sourceDrawable = AppCompatResources.getDrawable(context, R.drawable.location_pin)
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

        point: Point,
        mapboxMap: MapboxMap,
        zoom: Double
    ) {
        mapboxMap.flyTo(
            cameraOptions {
                center(
                    point
                ) // Sets the new camera position on click point
                zoom(zoom) // Sets the zoom
                bearing(270.0) // Rotate the camera
                pitch(50.0) // Set the camera pitch
            },
            mapAnimationOptions {
                duration(3000)
            }
        )
    }


    // Draw marker Code
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
        val sourceDrawable = AppCompatResources.getDrawable(context, R.drawable.location_pin)
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
            mapAnimationOptions {
                duration(3000)
            }
        )
    }
/*
    private fun point(point: Point) {
        if (check) {
            latOrigin = point.latitude()
            lonOrigin = point.longitude()

            //   list.add(Point.fromLngLat(originlong, originlat))
            list.set(0,Point.fromLngLat(lonOrigin, latOrigin))

        } else {
            latDestination = point.latitude()
            lonDestination = point.longitude()
            if (flag == false) {
                list.add(Point.fromLngLat(lonDestination, latDestination))

            } else {

                list.set(1,Point.fromLngLat(lonDestination,latDestination))
            }

        }
*/

      /*  println("originlat" + originlat)
        println("originlong" + originlong)
        println("destinationlat" + destinationlat)
        println("destinationlong" + destinationlong)
//        Log.d("TAG", "point: $point")*/
//        Log.d("TAG", "point: ${point.latitude()}")
//        Log.d("TAG", "point: ${point.longitude()}")



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

