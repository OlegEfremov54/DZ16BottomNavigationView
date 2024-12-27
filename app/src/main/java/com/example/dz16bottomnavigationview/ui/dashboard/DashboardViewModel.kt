package com.example.dz16bottomnavigationview.ui.dashboard

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dz16bottomnavigationview.R
import com.example.dz16bottomnavigationview.domain.weather.models.CurrentWeather
import com.example.dz16bottomnavigationview.domain.weather.utils.RetrofitInstance
import kotlinx.coroutines.launch
import java.io.IOException

class DashboardViewModel (private val locationManager: LocationManager, private val apiKey: String) :
    ViewModel() {
    val city = MutableLiveData<String>()
    val temp = MutableLiveData<String>()
    val pressure = MutableLiveData<String>()
    val humidity = MutableLiveData<String>()
    val windDeg = MutableLiveData<String>()
    val windSpeed = MutableLiveData<String>()
    val iconUrl = MutableLiveData<String>()
    private val byLocation = MutableLiveData<Boolean>().apply { value = true }
    private val lat = MutableLiveData<Double>().apply { value = 0.0 }
    private val lon = MutableLiveData<Double>().apply { value = 0.0 }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getCurrentWeatherByCity(_city: String) = viewModelScope.launch {
        byLocation.value = false

        val response = try {
            RetrofitInstance.api.getCurrentWeatherByCity(
                city = _city,
                units = "metric",
                apiKey = apiKey
            )
        } catch (e: IOException) {
            Log.d("EXC", "${e.message}")
            return@launch
        } catch (e: HttpException) {
            Log.d("EXC", "${e.message}")
            return@launch
        }
        val data = response.body()
        Log.d("data", response.body().toString())
        if (response.isSuccessful && data != null) {
            updateData(data)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getCurrentWeatherByLocation() {
        Log.d("location", "starting getLocation()")
        Log.d("location", "location manager = $locationManager ")
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000 * 10,
                500f
            )
            { location ->
                lat.value = location.latitude
                lon.value = location.longitude
                if (byLocation.value!!) updateCurrentWeatherByLocation()
            }
        } catch (e: SecurityException) {
            Log.d("location", "returned NULL")
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun updateCurrentWeatherByLocation() = viewModelScope.launch {
        val response = try {
            RetrofitInstance.api.getCurrentWeatherByLocation(
                lat = lat.value.toString(),
                lon = lon.value.toString(),
                units = "metric",
                apiKey = apiKey
            )
        } catch (e: IOException) {
            Log.d("EXC", "${e.message}")
            return@launch
        } catch (e: HttpException) {
            Log.d("EXC", "${e.message}")
            return@launch
        }
        val data = response.body()
        if (response.isSuccessful && data != null) {
            updateData(data)
        }

    }

    private fun updateData(data: CurrentWeather) {
        city.value = data.name
        temp.value = data.main.temp.toInt()
            .toString() + " (" + data.main.temp_min.toInt() + "..." + data.main.temp_max.toInt() + ")"
        pressure.value = (data.main.pressure / 1.33).toInt().toString()
        humidity.value = data.main.humidity.toString()
        windDeg.value = data.wind.deg.toString()
        windSpeed.value = data.wind.speed.toString()
        val iconId = data.weather[0].icon
        iconUrl.value = "https://openweathermap.org/img/wn/$iconId@2x.png"
    }
}