package com.note.app.myfirstapp.location

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.mapbox.api.directions.v5.models.Bearing
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.extensions.applyLanguageAndVoiceUnitOptions
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.RouterCallback
import com.mapbox.navigation.base.route.RouterFailure
import com.mapbox.navigation.base.route.RouterOrigin
import com.mapbox.navigation.core.MapboxNavigationProvider
import com.note.app.myfirstapp.R
import com.note.app.myfirstapp.databinding.ActivityFetchRouteBinding

class FetchRouteActivity : AppCompatActivity() {



    private val mapboxNavigation by lazy {
        if (MapboxNavigationProvider.isCreated()) {
            MapboxNavigationProvider.retrieve()
        } else {
            MapboxNavigationProvider.create(
                NavigationOptions.Builder(this)
                    .accessToken(getString(R.string.maptoken))
                    .build()
            )
        }
    }
    private val originLocation = Location("test").apply {
        longitude = -122.4192
        latitude = 37.7627
        bearing = 10f
    }
    private val destination = Point.fromLngLat(-122.4106, 37.7676)


    lateinit var  binding:ActivityFetchRouteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFetchRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchARoute()

    }

    private fun fetchARoute() {
        val originPoint = Point.fromLngLat(
            originLocation.longitude,
            originLocation.latitude
        )

        val routeOptions = RouteOptions.builder()
// applies the default parameters to route options
            .applyDefaultNavigationOptions()
            .applyLanguageAndVoiceUnitOptions(this)
// lists the coordinate pair i.e. origin and destination
// If you want to specify waypoints you can pass list of points instead of null
            .coordinatesList(listOf(originPoint, destination))
// set it to true if you want to receive alternate routes to your destination
            .alternatives(false)
// provide the bearing for the origin of the request to ensure
// that the returned route faces in the direction of the current user movement
            .bearingsList(
                listOf(
                    Bearing.builder()
                        .angle(originLocation.bearing.toDouble())
                        .degrees(45.0)
                        .build(),
                    null
                )
            )
            .build()
        mapboxNavigation.requestRoutes(
            routeOptions,
            object : RouterCallback {
                /**
                 * The callback is triggered when the routes are ready to be displayed.
                 */
                override fun onRoutesReady(
                    routes: List<DirectionsRoute>,
                    routerOrigin: RouterOrigin
                ) {
// GSON instance used only to print the response prettily
                    val gson = GsonBuilder().setPrettyPrinting().create()


                }

                /**
                 * The callback is triggered if the request to fetch a route was canceled.
                 */
                override fun onCanceled(routeOptions: RouteOptions, routerOrigin: RouterOrigin) {
// This particular callback is executed if you invoke
// mapboxNavigation.cancelRouteRequest()

                }

                /**
                 * The callback is triggered if the request to fetch a route failed for any reason.
                 */
                override fun onFailure(reasons: List<RouterFailure>, routeOptions: RouteOptions) {


                }
            }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        mapboxNavigation.onDestroy()
    }
}

