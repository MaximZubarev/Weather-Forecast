package com.mldz.weatherforecat.mvp.model

import com.mldz.weatherforecat.utils.Api
import com.mldz.weatherforecat.utils.model.Forecast
import io.reactivex.Observable

/**
Created by Maxim Zubarev on 2019-08-23.
 */

class MainActivityModel {
    fun getWeatherNow(location: String): Observable<Forecast> {
        return Api.create().getWeatherNow(location)
    }
}