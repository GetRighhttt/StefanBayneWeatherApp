package com.example.stefanbayneweatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stefanbayneweatherapp.R
import com.example.stefanbayneweatherapp.databinding.FragmentMainPageBinding
import com.example.stefanbayneweatherapp.repository.WeatherDataRepositoryImpl
import com.example.stefanbayneweatherapp.viewmodel.WeatherViewModel


class MainPageFragment : Fragment() {

    private var _binding: FragmentMainPageBinding? = null
    val binding: FragmentMainPageBinding get() = _binding!!

    lateinit var weatherForecastAdapter: WeatherForecastAdapter

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
        _binding = FragmentMainPageBinding.inflate(layoutInflater)


        binding.btnToLookupCity.setOnClickListener {
            val etCityName = binding.etEnterCity.text.toString()

            if(etCityName.isNotEmpty()) {
            Toast.makeText(binding.root.context, "TEST", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container,
                FragmentCityDisplayModel.newInstance(binding.etEnterCity.text.toString()))
                .addToBackStack(null)
                .commit()
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}



