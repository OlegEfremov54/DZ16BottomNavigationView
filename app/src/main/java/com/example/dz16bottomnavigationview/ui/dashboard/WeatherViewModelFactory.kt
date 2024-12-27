package com.example.dz16bottomnavigationview.ui.dashboard

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.dz16bottomnavigationview.R


class WeatherViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val apiKey = context.getString(R.string.api)

    private val locationManager: LocationManager =
        context.getSystemService(LOCATION_SERVICE) as LocationManager

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(locationManager = locationManager, apiKey = apiKey) as T
    }

}