package com.example.stefanbayneweatherapp.api

import android.provider.ContactsContract
import com.example.stefanbayneweatherapp.model.DataResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeatherService {

    /**
     * GET = receiving the query from the API link!!
     * suspend = the function we call so the background data is not being used
     *  on the main thread. Increases speed, and functionality..
     * The queries directly correspond with the link provided by the API..
     * The "parameters" section of the API website outlines specific queries
     * that we would need to use. Lastly, this GET function is a Response from the
     * internet to the data class.
     */

    // https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    @GET("forecast/")
    suspend fun getWeatherData(
        @Query("q") q: String? = "Tampa",
        @Query("appid") appid: String? = "ea5271714531dee2d47e29e7eadee5d0",
        @Query("units") units: String? = "metric"
    ): Response<DataResponse>

    // static object to get the weather from the API.
    companion object {
        /**
         * We first declare an instance and initiate it to our API and null
         * Then declare that if its null(no memory detected), we begin our retrofit build
         * We initiate instance to retrofit builder, set the base URL,
         * convert it to GSON, build the API, and then create it and set it to be an instance
         * of the Java library.
         * Then we return our initial variable "instance"
         */

        private var instance: ApiWeatherService? =null
        fun getApiWeatherService(): ApiWeatherService {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiWeatherService::class.java)
            }
            return instance!!
        }
    }
}