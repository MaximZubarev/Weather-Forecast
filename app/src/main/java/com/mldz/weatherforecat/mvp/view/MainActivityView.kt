package com.mldz.weatherforecat.mvp.view

import com.mldz.weatherforecat.utils.model.Forecast

/**
Created by Maxim Zubarev on 2019-08-23.
 */
interface MainActivityView {
    fun setData(forecast: Forecast)
    fun showProgressBar()
    fun enableProgressBar()
}