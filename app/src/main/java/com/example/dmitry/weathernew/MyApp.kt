package com.example.dmitry.weathernew

import android.app.Application
import com.example.dmitry.weathernew.models.WeatherData

class MyApp : Application() {

    companion object {
        var currentWeatherData: WeatherData? = null
    }

    override fun onCreate() {
        super.onCreate()
    }
}