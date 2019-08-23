package com.mldz.weatherforecat.utils.model

data class Forecast(
    val clouds: Clouds,
    val main: Main,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)