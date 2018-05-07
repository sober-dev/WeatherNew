package com.example.dmitry.weathernew.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dmitry.weathernew.MyApp
import com.example.dmitry.weathernew.R
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
        val weatherData = MyApp.currentWeatherData
        if (weatherData != null) {
            textViewCityName.text = "City: " + weatherData.name
            textViewTemperature.text = "Temperature: " + weatherData.main.temp.toString()
            textViewHumidity.text = "Humidity: " + weatherData.main.humidity.toString()
            textViewWindSpeed.text = "WindSpeed: " + weatherData.wind.speed.toString()

            weatherData.weather.forEach {
                Picasso.get().load("http://api.openweathermap.org/img/w/" + it.icon + ".png").into(imageViewIcon)
            }
        }
    }
}