package com.example.dmitry.weathernew.ui.fragments

import android.app.FragmentTransaction
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dmitry.weathernew.R
import com.example.dmitry.weathernew.jobscheduler.MyJobService
import com.example.dmitry.weathernew.models.WeatherData
import com.example.dmitry.weathernew.ui.adapters.CityAutoCompleteAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_city.*
import java.util.concurrent.TimeUnit

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

        weatherButton.setOnClickListener {
            navigateToWeatherFragment()
            scheduleJob()
        }

        cityAutoCompleteTextView.setAdapter(CityAutoCompleteAdapter(activity as Context))
        cityAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            val currentWeatherData = getSavedWeatherData()
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
                .commit()
    }

    private fun getSavedWeatherData(): WeatherData? {
        val sharedPref = context?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return null
        val jsonData = sharedPref.getString(getString(R.string.weather_data_key), null)
        return Gson().fromJson(jsonData, WeatherData::class.java)
    }

    private fun scheduleJob() {
        val jobId = 34723675
        val jobInfo = JobInfo.Builder(jobId, ComponentName(context, MyJobService::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(TimeUnit.DAYS.toMillis(1))
                .build()
        (context?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler).schedule(jobInfo)
    }
}
