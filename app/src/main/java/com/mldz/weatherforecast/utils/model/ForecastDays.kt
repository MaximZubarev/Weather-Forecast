package com.mldz.weatherforecast.utils.model

import kotlin.collections.List

/**
Created by Maxim Zubarev on 2019-08-24.
 */
data class ForecastDays(
    val list: List<com.mldz.weatherforecast.utils.model.List>,
    val city: City
)