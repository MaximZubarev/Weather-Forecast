package com.mldz.weatherforecast.mvp.view

import com.mldz.weatherforecast.utils.model.FullForecast

/**
Created by Maxim Zubarev on 2019-08-23.
 */
interface MainActivityView {
    fun setData(fullForecast: FullForecast? = null)
    fun showProgressBar()
    fun enableProgressBar()
    fun showError(text: String)
    fun showTextError(flag: Boolean)
}