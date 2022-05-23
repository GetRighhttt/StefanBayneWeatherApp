package com.example.stefanbayneweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stefanbayneweatherapp.model.DataResponse
import com.example.stefanbayneweatherapp.model.ListForWeather
import com.example.stefanbayneweatherapp.repository.WeatherDataRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *  Now, we are going to use the API, data classes, and repository information to start
 *  implementing and creating a view model. We initialize the get Weather method when the
 *  View Model is first called.
 *  We set a variable to be equal to the list of the weather, and then use Coroutines to
 *  suspend background information away from the main thread, and then get weather data from
 *  the API.
 */

class WeatherViewModel (
    private val repositoryImpl: WeatherDataRepositoryImpl
    ): ViewModel() {

//    init {
//        getWeatherData(null, null, null)
//    }

    private val _weatherData = MutableLiveData<DataResponse>()

    val weatherData: LiveData<DataResponse> get() = _weatherData

    fun getWeatherData(cityName: String, units: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositoryImpl.getWeatherData(cityName = cityName, units = units)
            _weatherData.postValue(response)
        }
    }
}