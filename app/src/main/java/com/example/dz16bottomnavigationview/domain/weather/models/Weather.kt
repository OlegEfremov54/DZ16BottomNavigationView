package com.example.dz16bottomnavigationview.domain.weather.models

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)