package com.mldz.weatherforecast

import android.content.Context
import android.graphics.Typeface
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
Created by Maxim Zubarev on 2019-08-25.
 */

fun getIcon(actualId: Int, sunrise: Long, sunset: Long, context: Context): String {
    val id = actualId / 100
    var icon = ""
    if (actualId == 800) {
        val currentTime = Date().time
        icon = if (currentTime in sunrise until sunset) {
            context.getString(R.string.weather_sunny)
        } else {
            context.getString(R.string.weather_clear_night)
        }
    } else {
        when (id) {
            2 -> icon = context.getString(R.string.weather_thunder)
            3 -> icon = context.getString(R.string.weather_drizzle)
            7 -> icon = context.getString(R.string.weather_foggy)
            8 -> icon = context.getString(R.string.weather_cloudy)
            6 -> icon = context.getString(R.string.weather_snowy)
            5 -> icon = context.getString(R.string.weather_rainy)
        }
    }
    return icon
}