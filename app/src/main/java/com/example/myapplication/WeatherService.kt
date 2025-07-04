package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherService {
    // Full dynamic URL since wttr.in is simple and plain text
    @GET
    fun getTemperature(@Url url: String): Call<String>
}