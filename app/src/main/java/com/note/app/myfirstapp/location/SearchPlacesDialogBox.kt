package com.note.app.myfirstapp.location

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.search.OfflineSearchEngineSettings
import com.mapbox.search.ResponseInfo
import com.mapbox.search.SearchEngineSettings
import com.mapbox.search.record.HistoryRecord
import com.mapbox.search.result.SearchResult
import com.mapbox.search.result.SearchSuggestion
import com.mapbox.search.ui.view.CommonSearchViewConfiguration
import com.mapbox.search.ui.view.DistanceUnitType
import com.mapbox.search.ui.view.SearchResultsView
import com.note.app.myfirstapp.databinding.SearchPlacesDialogBinding
import com.note.app.myfirstapp.helper.AppUtils
import com.note.app.myfirstapp.location.callBack.SearchLocation

class SearchPlacesDialogBox(myContext: Context,private val callSearchLocation: SearchLocation) :Dialog(myContext){
    lateinit var binding: SearchPlacesDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(true)
        window!!.requestFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding= SearchPlacesDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancel.setOnClickListener {
            binding.search.text.clear()
            binding.searchView.visibility= View.GONE
            dismiss()
        }
        binding.search.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length == 0)
                    binding.searchView.visibility = View.GONE
                else {
                    binding.searchView.visibility=View.VISIBLE
                    binding.searchView.search(p0.toString())
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
            searchWithText()
    }
    private fun searchWithText() {
        binding.searchView.initialize(
            SearchResultsView.Configuration(
                commonConfiguration = CommonSearchViewConfiguration(DistanceUnitType.IMPERIAL),
                searchEngineSettings = SearchEngineSettings(AppUtils.accessToken),
                offlineSearchEngineSettings = OfflineSearchEngineSettings(AppUtils.accessToken)
            )
        )
        binding.searchView.addSearchListener(object : SearchResultsView.SearchListener {

            private fun showToast(message: String) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            override fun onHistoryItemClicked(historyRecord: HistoryRecord) {
//                onCameraTrackingDismissed()
//                showToast("HistoryRecord clicked: ${historyRecord.name}")
                historyRecord.coordinate?.let {
//                    addAnnotationToMap(this@LocationMainActivity,it.latitude(),it.longitude(),mapView!!)
                    /*         addAnnotationToMap(
                                 historyRecord.coordinate!!.longitude(),
                                 it.latitude(), historyRecord.name
                             )*/
                    callSearchLocation.getSearchLocation(it,historyRecord.name)
                    binding.searchView.visibility = View.VISIBLE
                    dismiss()
                      //////// lat ln
                }
            }
            override fun onSearchResult(searchResult: SearchResult, responseInfo: ResponseInfo) {
//                onCameraTrackingDismissed()
                Log.e("Search:", "onSearchResult: ${searchResult.address}" )
//                showToast("SearchResult clicked: ${searchResult.name}")
                searchResult.coordinate?.let {
                    /*        addAnnotationToMap(
                                searchResult.coordinate!!.longitude(),
                                it.latitude(), searchResult.name
                            )*/
//                    addAnnotationToMap(this@LocationMainActivity,it.latitude(),it.longitude(),mapView!!)
//
                    callSearchLocation.getSearchLocation(it,searchResult.name)
                    binding.searchView.visibility = View.VISIBLE
                    dismiss()
                }
   //  ////// lat ln
            }
            override fun onPopulateQueryClicked(
                suggestion: SearchSuggestion,
                responseInfo: ResponseInfo
            ) {
                binding.search?.setText(suggestion.name)
            }
            override fun onCategoryResult(
                suggestion: SearchSuggestion,
                results: List<SearchResult>,
                responseInfo: ResponseInfo
            ) {
                // not implemented
            }
            override fun onError(e: Exception) {
                // not implemented
                Log.e("Search:", "Failed: $e" )

            }
            override fun onFeedbackClicked(responseInfo: ResponseInfo) {
                // not implemented
            }
            override fun onOfflineSearchResults(
                results: List<SearchResult>,
                responseInfo: ResponseInfo
            ) {
                // not implemented
            }
            override fun onSuggestions(
                suggestions: List<SearchSuggestion>,
                responseInfo: ResponseInfo
            ) {
                // not implemented
            }
        })
    }
}