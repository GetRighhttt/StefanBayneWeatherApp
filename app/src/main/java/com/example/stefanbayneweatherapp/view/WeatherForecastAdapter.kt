package com.example.stefanbayneweatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stefanbayneweatherapp.databinding.DisplayWeatherInfoBinding
import com.example.stefanbayneweatherapp.model.ListForWeather

// Adapter for the recycler view to show the information from the display info fragment..

// We first create two variables that are initialized to the mutable list from the API
// We extend the recycler class and pass in the view Model list
class WeatherForecastAdapter (
    private val weatherList: MutableList<ListForWeather> = mutableListOf(),
    private val openDetails: (ListForWeather) -> Unit
): RecyclerView.Adapter<WeatherForecastAdapter.WeatherViewModel>() {

    fun setUserList(newList: List<ListForWeather>) {
        weatherList.clear()
        weatherList.addAll(newList)
        notifyDataSetChanged()
    }

    // we create an inner class to bind the info to the view model to get our information from the
    // API and repository
    inner class WeatherViewModel(
        private val binding: DisplayWeatherInfoBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(listForWeather: ListForWeather) {

            binding.tvDisplayTempType.text = listForWeather.weather[0].main
            binding.tvDisplayDegrees.text = listForWeather.main.temp.toString()

            binding.root.setOnClickListener{
                openDetails(listForWeather)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeatherViewModel(
        DisplayWeatherInfoBinding.inflate( LayoutInflater.from(parent.context),
        parent, false )
        )

    override fun onBindViewHolder(holder: WeatherViewModel, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount() = weatherList.size
}