package com.example.dmitry.weathernew.models

import com.google.gson.annotations.SerializedName

data class WeatherData(
        @SerializedName("coord") val coord: Coord,
        @SerializedName("weather") val weather: List<Weather>,
        @SerializedName("base") val base: String,
        @SerializedName("main") val main: Main,
        @SerializedName("visibility") val visibility: Double,
        @SerializedName("wind") val wind: Wind,
        @SerializedName("clouds") val clouds: Clouds,
        @SerializedName("dt") val dt: Int,
        @SerializedName("sys") val sys: Sys,
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("cod") val cod: Int
) {
    data class Sys(
            @SerializedName("type") val type: Int,
            @SerializedName("id") val id: Int,
            @SerializedName("message") val message: Double,
            @SerializedName("country") val country: String,
            @SerializedName("sunrise") val sunrise: Int,
            @SerializedName("sunset") val sunset: Int
    )

    data class Coord(
            @SerializedName("lon") val lon: Double,
            @SerializedName("lat") val lat: Double
    )

    data class Main(
            @SerializedName("temp") val temp: Double,
            @SerializedName("pressure") val pressure: Double,
            @SerializedName("humidity") val humidity: Double,
            @SerializedName("temp_min") val tempMin: Double,
            @SerializedName("temp_max") val tempMax: Double
    )

    data class Weather(
            @SerializedName("id") val id: Int,
            @SerializedName("main") val main: String,
            @SerializedName("description") val description: String,
            @SerializedName("icon") val icon: String
    )

    data class Clouds(
            @SerializedName("all") val all: Double
    )

    data class Wind(
            @SerializedName("speed") val speed: Double,
            @SerializedName("deg") val deg: Double
    )
}
