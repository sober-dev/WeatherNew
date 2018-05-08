package com.example.dmitry.weathernew.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import com.example.dmitry.weathernew.R
import com.example.dmitry.weathernew.models.WeatherData
import com.example.dmitry.weathernew.network.WeatherApiService
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val currentCityId = sharedPref.getInt(getString(R.string.city_id_key), 0)
        WeatherApiService.create().getWeatherDataByCityId(currentCityId, applicationContext.getString(R.string.open_weather_api_key))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    saveCurrentWeatherData(result)
                }, { error ->
                    error.printStackTrace()
                })
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    private fun saveCurrentWeatherData(weatherData: WeatherData) {
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        sharedPref.edit()
                .putString(getString(R.string.weather_data_key), Gson().toJson(weatherData))
                .apply()
    }
}
