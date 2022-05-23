package com.example.stefanbayneweatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stefanbayneweatherapp.R
import com.example.stefanbayneweatherapp.databinding.FragmentCityDisplayModelBinding
import com.example.stefanbayneweatherapp.databinding.FragmentWeatherForecastPerCityModelBinding
import com.example.stefanbayneweatherapp.model.ListForWeather
import com.example.stefanbayneweatherapp.repository.WeatherDataRepositoryImpl
import com.example.stefanbayneweatherapp.viewmodel.WeatherViewModel

class FragmentCityDisplayModel() : Fragment() {

    private var _binding: FragmentCityDisplayModelBinding? = null
    private val binding: FragmentCityDisplayModelBinding get() = _binding!!
    lateinit var weatherForecastAdapter: WeatherForecastAdapter


    private val viewModel: WeatherViewModel by lazy {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return WeatherViewModel(WeatherDataRepositoryImpl()) as T
            }
        }.create(WeatherViewModel::class.java)
    }


    companion object {
        const val KEY = "CityDisplayFragmentKey"
        fun newInstance(cityName: String): FragmentCityDisplayModel {
            val fragment = FragmentCityDisplayModel()
            val bundle = Bundle()
            bundle.putString(KEY,cityName)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val listForWeather: ListForWeather? = arguments?.getParcelable(KEY)
        _binding = FragmentCityDisplayModelBinding.inflate(layoutInflater)
        val cityName = arguments?.getString(KEY)
        if(cityName != null) {
            viewModel.getWeatherData(cityName, "metric")
            configureObserver()
        }

//        binding.btnSearch.setOnClickListener{
////            filterUsers()
//        }
        return binding.root
    }

//    private fun filterUsers() {
//        // spinner gender or lego
//        when(binding.spChooseTemperature.selectedItem) {
//            "Select Preferred Temperature" -> viewModel.getWeatherData(null, null, null)
//            "Celsius" -> viewModel.getWeatherData(null, null, "metric")
//            "Kelvin" -> viewModel.getWeatherData(null, null, "standard")
//            "Fahrenheit" -> viewModel.getWeatherData(null, null, "imperial")
//        }
//    }

    private fun configureObserver() {
       weatherForecastAdapter = WeatherForecastAdapter(openDetails = ::openDetails)
        //viewLifecycleOwner -> points to the owner of the ViewModel
        viewModel.weatherData.observe(viewLifecycleOwner) { response ->
            if (response.list.isEmpty()) {
                binding.tvErrorText.text = "Network call failed"
            } else {
                weatherForecastAdapter.setUserList(response.list)
                binding.apply {
                    rvForecast.adapter = weatherForecastAdapter
                    pbLoading.visibility = View.GONE
                    tvErrorText.visibility = View.GONE
                }
            }
        }
    }

    private fun openDetails(listForWeather: ListForWeather) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,
                FragmentWeatherForecastPerCityModel.newInstance(listForWeather))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
