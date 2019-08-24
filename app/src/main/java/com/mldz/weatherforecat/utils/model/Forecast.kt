package com.mldz.weatherforecat.utils.model

import kotlin.collections.List

data class Forecast(
    val clouds: Clouds,
    val main: Main,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind,
    val name: String
)