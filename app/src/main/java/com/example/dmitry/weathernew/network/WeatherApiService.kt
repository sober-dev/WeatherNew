package com.example.dmitry.weathernew.network

import com.example.dmitry.weathernew.models.WeatherData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    fun getWeatherDataByCityName(
            @Query("q") cityName: String,
            @Query("appid") apiKey: String
    ): Observable<WeatherData>

    @GET("weather")
    fun getWeatherDataByCityId(
            @Query("id") cityId: Int,
            @Query("appid") apiKey: String
    ): Observable<WeatherData>

    companion object Factory {
        fun create(): WeatherApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .client(client)
                    .build()

            return retrofit.create(WeatherApiService::class.java)
        }
    }
}