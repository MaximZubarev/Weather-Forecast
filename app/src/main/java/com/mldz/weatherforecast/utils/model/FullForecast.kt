package com.mldz.weatherforecast.utils.model

import com.google.gson.GsonBuilder



/**
Created by Maxim Zubarev on 2019-08-24.
 */
data class FullForecast(
    val forecast: Forecast,
    val forecastDays: ForecastDays
) {
    fun toJson(): String {
        return GsonBuilder().create().toJson(this, FullForecast::class.java)
    }
}