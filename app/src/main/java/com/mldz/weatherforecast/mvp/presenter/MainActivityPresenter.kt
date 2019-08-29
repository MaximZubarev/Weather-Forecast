package com.mldz.weatherforecast.mvp.presenter

import android.util.Log
import androidx.annotation.Nullable
import com.google.gson.Gson
import com.mldz.weatherforecast.mvp.model.MainActivityModel
import com.mldz.weatherforecast.mvp.view.MainActivityView
import com.mldz.weatherforecast.utils.hasInternetConnection
import com.mldz.weatherforecast.utils.model.Forecast
import com.mldz.weatherforecast.utils.model.FullForecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.function.BiFunction


/**
Created by Maxim Zubarev on 2019-08-23.
 */
class MainActivityPresenter(private var model: MainActivityModel, private var disposables: CompositeDisposable) {
    private var view: MainActivityView? = null

    fun bindView(view: MainActivityView) {
        this.view = view
    }

    fun onCreate(location: String) {
        view?.showProgressBar()
        disposables.add(
            hasInternetConnection().subscribe(
                {
                    if (it) {
                        disposables.add(model.get(location)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                { forecast ->
                                    view?.setData(forecast)
                                    model.saveToBd(forecast, forecast.forecast.name)
                                }, {
                                    e -> Log.d("logs", "onError: ${e.message}")
                                    view?.enableProgressBar()
                                    view?.showTextError(false)
                                }, {
                                    view?.enableProgressBar()
                                    view?.showTextError(false)
                                }
                            ))
                    } else {
                        val gson = Gson()
                        disposables.add(model.getForecastFromDb(location)
                            .subscribeOn(Schedulers.io())
                            .map{ s -> gson.fromJson(s, FullForecast::class.java)}
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ forecast ->
                                view?.setData(forecast)
                                view?.showError("No internet connection. The forecast is not updated from last time")
                                view?.showTextError(false)
                            }, {
                                e -> Log.d("logs", "onError: ${e.message}")
                                view?.showError("We don not have latest forecast for this place")
                                view?.setData()
                                view?.showTextError(true)
                                view?.enableProgressBar()
                            }, {
                                view?.enableProgressBar()
                            }))
                    }
                }, {
                    Log.d("tags", it.message)
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