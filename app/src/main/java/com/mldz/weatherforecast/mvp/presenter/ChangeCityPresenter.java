package com.mldz.weatherforecast.mvp.presenter;

import com.mldz.weatherforecast.ChangeCity;
import com.mldz.weatherforecast.mvp.model.ChangeCityModel;
import com.mldz.weatherforecast.mvp.view.ChangeCityView;
import io.reactivex.disposables.CompositeDisposable;

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
        view.setData(model.getData());
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
