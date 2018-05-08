package com.example.dmitry.weathernew.ui.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.dmitry.weathernew.R
import com.example.dmitry.weathernew.ui.fragments.CityFragment
import com.example.dmitry.weathernew.ui.fragments.WeatherFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            return
        }

        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val fragment = if (sharedPref.getString(getString(R.string.weather_data_key), null) != null)
            WeatherFragment()
        else
            CityFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
    }
}
