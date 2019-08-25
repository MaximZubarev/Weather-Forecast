package com.mldz.weatherforecast.utils.model

import kotlin.collections.List

data class List(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>
)