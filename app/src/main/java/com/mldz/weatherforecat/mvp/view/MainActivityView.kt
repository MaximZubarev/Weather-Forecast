package com.mldz.weatherforecat.mvp.view

import com.mldz.weatherforecat.utils.model.Forecast
import com.mldz.weatherforecat.utils.model.FullForecast

/**
Created by Maxim Zubarev on 2019-08-23.
 */
interface MainActivityView {
    fun setData(fullForecast: FullForecast)
    fun showProgressBar()
    fun enableProgressBar()
}