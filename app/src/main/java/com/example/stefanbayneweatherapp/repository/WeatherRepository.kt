package com.example.stefanbayneweatherapp.repository

import com.example.stefanbayneweatherapp.api.ApiWeatherService
import com.example.stefanbayneweatherapp.model.DataResponse

interface WeatherRepository {
    // function to pass in parameters from API
    suspend fun getWeatherData (q: String, units: String): DataResponse
}

class WeatherDataRepositoryImpl
    (
    private val service: ApiWeatherService = ApiWeatherService
        .getApiWeatherService()): WeatherRepository {
    override suspend fun getWeatherData(cityName: String, units: String): DataResponse {
        val response = service.getWeatherData(q = cityName, units = units)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            DataResponse(emptyList())
        }
    }
}

