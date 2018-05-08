package com.example.dmitry.weathernew.ui.fragments

import android.app.FragmentTransaction
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.dmitry.weathernew.R
import com.example.dmitry.weathernew.models.WeatherData
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cityButton.setOnClickListener { navigateToCityFragment() }

        val weatherData = getSavedWeatherData()
        if (weatherData != null) {
            textViewCityName.text = "City: " + weatherData.name
            textViewTemperature.text = "Temperature: " + weatherData.main.temp.toString()
            textViewHumidity.text = "Humidity: " + weatherData.main.humidity.toString()
            textViewWindSpeed.text = "WindSpeed: " + weatherData.wind.speed.toString()

            weatherData.weather.forEach {
                val imageViewIcon = ImageView(context)
                imagesContainer.addView(imageViewIcon)
                Picasso.get().load("http://api.openweathermap.org/img/w/" + it.icon + ".png").into(imageViewIcon)
            }
        }
    }

    private fun getSavedWeatherData(): WeatherData? {
        val sharedPref = context?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return null
        val jsonData = sharedPref.getString(getString(R.string.weather_data_key), null)
        return Gson().fromJson(jsonData, WeatherData::class.java)
    }

    private fun navigateToCityFragment() {
        fragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, CityFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }
}