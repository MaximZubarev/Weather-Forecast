package com.mldz.weatherforecast.mvp.presenter;

import android.util.Log;
import com.mldz.weatherforecast.ChangeCity;
import com.mldz.weatherforecast.mvp.model.ChangeCityModel;
import com.mldz.weatherforecast.mvp.view.ChangeCityView;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

/**
 * Created by Maxim Zubarev on 2019-08-26.
 */
public class ChangeCityPresenter {
    private ChangeCityModel model;
    private ChangeCityView view;
    private CompositeDisposable disposables;

    public ChangeCityPresenter(ChangeCityModel model, CompositeDisposable disposables) {
        this.model = model;
        this.disposables = disposables;
    }

    public void bind(ChangeCityView view) {
        this.view = view;
    }

    public void onCreate() {
        disposables.add(model.getCities()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> strings) {
                        if (view != null) {
                            if (strings.size() > 0) {
                                view.setData(strings);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("logs", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void saveCity(String name) {
        model.saveCity(name);
    }

    public void unBind() {
        view = null;
    }

    public void onDestroy(){
        unBind();
        if (disposables != null) {
            disposables.clear();
        }
    }
}
