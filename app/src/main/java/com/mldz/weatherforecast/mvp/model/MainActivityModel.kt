package com.mldz.weatherforecast.mvp.model

import android.content.Context
import com.mldz.weatherforecast.SavePref
import com.mldz.weatherforecast.db.DbManager
import com.mldz.weatherforecast.utils.Api
import com.mldz.weatherforecast.utils.model.Forecast
import com.mldz.weatherforecast.utils.model.ForecastDays
import com.mldz.weatherforecast.utils.model.FullForecast
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
Created by Maxim Zubarev on 2019-08-23.
 */

class MainActivityModel(val context: Context) {
    private fun getWeatherNow(location: String): Observable<Forecast> {
        return Api.create().getWeatherNow(location)
    }

    private fun getWeatherDays(location: String): Observable<ForecastDays> {
        return Api.create().getWeatherDays(location)
    }

    fun get(location: String): Observable<FullForecast> {
        val value = if (getCity() == null)
            location
        else
            getCity()!!

        return Observable.zip(
            getWeatherNow(value),
            getWeatherDays(value),
            BiFunction<Forecast, ForecastDays, FullForecast> { t1, t2 -> FullForecast(t1, t2) }
        )
    }

    private fun getCity(): String? {
        return SavePref.getInstance(context).city
    }

    fun saveToBd(forecast: FullForecast, city: String) {
        DbManager.getInstance(context).saveForecast(city, forecast)
    }

    fun getForecastFromDb(city: String): Observable<String> {
        return Observable.just(DbManager.getInstance(context).getForecast(city))
    }
}