package com.mldz.weatherforecat.utils.model

import kotlin.collections.List

data class List(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>
)