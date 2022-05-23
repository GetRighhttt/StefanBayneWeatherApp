package com.example.stefanbayneweatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stefanbayneweatherapp.databinding.FragmentWeatherForecastPerCityModelBinding
import com.example.stefanbayneweatherapp.model.ListForWeather
import com.example.stefanbayneweatherapp.repository.WeatherDataRepositoryImpl
import com.example.stefanbayneweatherapp.viewmodel.WeatherViewModel
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class FragmentWeatherForecastPerCityModel : Fragment() {

    private var _binding: FragmentWeatherForecastPerCityModelBinding? = null
    private val binding: FragmentWeatherForecastPerCityModelBinding get() = _binding!!
    lateinit var weatherForecastAdapter: WeatherForecastAdapter

    companion object {
        const val KEY = "WeatherForecastDisplay"
        fun newInstance(listForWeather: ListForWeather): FragmentWeatherForecastPerCityModel {
            val fragment = FragmentWeatherForecastPerCityModel()
            val bundle = Bundle()
            bundle.putParcelable(KEY,listForWeather)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: WeatherViewModel by lazy {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return WeatherViewModel(WeatherDataRepositoryImpl()) as T
            }
        }.create(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherForecastPerCityModelBinding.inflate(layoutInflater)


        val listForWeather: ListForWeather? = arguments?.getParcelable(KEY)

        binding.apply {
            tvTemperatureDisplay.text = listForWeather?.main?.temp.toString()
            tvClouds.text = listForWeather!!.weather[0].main.toString()
            tvFeelsLikeDisplay.text = listForWeather?.main?.feels_like.toString()
            tvDescriptionDisplay.text = listForWeather.weather[0].description.toString()
        }

//        val y: Double? = null
        binding.btnSearch.setOnClickListener {
            val spinnerTest = binding.spChooseTemperature.selectedItem.toString()
            when(spinnerTest) {
                "Kelvin" -> binding.tvTemperatureDisplay.text = convertMeasurements().absoluteValue.toString()
                "Fahrenheit" -> binding.tvTemperatureDisplay.text = convertMeasurements().absoluteValue.toString()
                else -> binding.tvTemperatureDisplay.text
            }
        }
        return binding.root
    }

    private fun convertMeasurements(celsiusT: Double = 273.15): Double {
       val kelvinT = celsiusT + 273.15
        val fahrenT = ((celsiusT/5) * 9) + 32
        return convertMeasurements(celsiusT)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}