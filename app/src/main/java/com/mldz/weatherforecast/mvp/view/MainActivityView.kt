package com.mldz.weatherforecast.mvp.view

import com.mldz.weatherforecast.utils.model.FullForecast

/**
Created by Maxim Zubarev on 2019-08-23.
 */
interface MainActivityView {
    fun setData(fullForecast: FullForecast)
    fun showProgressBar()
    fun enableProgressBar()
}