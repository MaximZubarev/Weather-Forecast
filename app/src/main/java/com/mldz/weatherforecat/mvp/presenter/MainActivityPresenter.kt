package com.mldz.weatherforecat.mvp.presenter

import android.util.Log
import com.mldz.weatherforecat.mvp.model.MainActivityModel
import com.mldz.weatherforecat.mvp.view.MainActivityView
import com.mldz.weatherforecat.utils.model.Forecast
import com.mldz.weatherforecat.utils.model.ForecastDays
import com.mldz.weatherforecat.utils.model.FullForecast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.functions.BiFunction


/**
Created by Maxim Zubarev on 2019-08-23.
 */
class MainActivityPresenter(private var model: MainActivityModel, private var disposables: CompositeDisposable) {
    private var view: MainActivityView? = null

    fun bindView(view: MainActivityView) {
        this.view = view
    }

    fun onCreate(location: String) {
        disposables.add(model.get(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {
                view?.setData(it)
                print(it.forecast.clouds.all)
            }, {
                e -> Log.d("logs", "onError: ${e.message}")
                view?.enableProgressBar()
            }, {
                view?.enableProgressBar()
            }
        ))
    }

    fun unBindView() {
        view = null
    }

    fun onDestroy() {
        unBindView()
        disposables.clear()
    }
}