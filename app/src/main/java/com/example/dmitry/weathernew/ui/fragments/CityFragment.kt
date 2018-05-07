package com.example.dmitry.weathernew.ui.fragments

import android.app.FragmentTransaction
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dmitry.weathernew.MyApp
import com.example.dmitry.weathernew.R
import com.example.dmitry.weathernew.ui.adapters.CityAutoCompleteAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_city.*

class CityFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        weatherButton.setOnClickListener { navigateToWeatherFragment() }

        cityAutoCompleteTextView.setAdapter(CityAutoCompleteAdapter(activity as Context))
        cityAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            val currentWeatherData = MyApp.currentWeatherData
            if (currentWeatherData != null) {
                showCityOnMap(LatLng(currentWeatherData.coord.lat, currentWeatherData.coord.lon))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun showCityOnMap(cityLatLng: LatLng) {
        mMap.addMarker(MarkerOptions().position(cityLatLng))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLatLng, 10f))
    }

    private fun navigateToWeatherFragment() {
        fragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, WeatherFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }
}
