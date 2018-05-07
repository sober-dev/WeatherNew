package com.example.dmitry.weathernew.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.dmitry.weathernew.R
import com.example.dmitry.weathernew.ui.fragments.CityFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            return
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, CityFragment())
                .commit()
    }
}
