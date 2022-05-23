package com.example.stefanbayneweatherapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Parcelize makes it easier for the IDE to organize the data
 * SerializedName allows us to convert from API name to a name we want to use
 * Data class for each of the values in the JSON format of the API..
*/

data class DataResponse
	(
	val list: List<ListForWeather>
	)

@Parcelize
data class ListForWeather
	(
	val dt: Long,
	val main: Main,
	val weather: List<Weather>,
	val name: String
	): Parcelable

@Parcelize
data class Main
	(
	val temp: Double,
	val feels_like: Double,
	val temp_min: Double,
	val temp_max: Double
	): Parcelable

@Parcelize
data class Weather
	(
	val id: Int,
	val main: String,
	val description: String
	): Parcelable



