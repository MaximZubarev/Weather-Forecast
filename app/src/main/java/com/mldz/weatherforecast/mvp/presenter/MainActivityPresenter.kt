package com.mldz.weatherforecast.mvp.presenter

import android.util.Log
import com.mldz.weatherforecast.mvp.model.MainActivityModel
import com.mldz.weatherforecast.mvp.view.MainActivityView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


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