package com.mldz.weatherforecat.mvp.model

import android.os.Build
import android.support.annotation.RequiresApi
import com.mldz.weatherforecat.utils.Api
import com.mldz.weatherforecat.utils.model.Forecast
import com.mldz.weatherforecat.utils.model.ForecastDays
import com.mldz.weatherforecat.utils.model.FullForecast
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
Created by Maxim Zubarev on 2019-08-23.
 */

class MainActivityModel {
    private fun getWeatherNow(location: String): Observable<Forecast> {
        return Api.create().getWeatherNow(location)
    }

    private fun getWeatherDays(location: String): Observable<ForecastDays> {
        return Api.create().getWeatherDays(location)
    }

    fun get(location: String): Observable<FullForecast> {
        return Observable.zip(
            getWeatherNow(location),
            getWeatherDays(location),
            BiFunction<Forecast, ForecastDays, FullForecast> { t1, t2 -> FullForecast(t1, t2) }
        )
    }
}