package com.mldz.weatherforecast.utils

import com.mldz.weatherforecast.utils.model.Forecast
import com.mldz.weatherforecast.utils.model.ForecastDays
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
Created by Maxim Zubarev on 2019-08-23.
 */

interface ApiEndpoint {
    @GET("data/2.5/weather")
    fun getWeatherNow(@Query("q") q: String): Observable<Forecast>

    @GET("data/2.5/forecast")
    fun getWeatherDays(@Query("q") q: String): Observable<ForecastDays>
}