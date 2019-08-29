package com.mldz.weatherforecast.mvp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.mldz.weatherforecast.SavePref;
import com.mldz.weatherforecast.db.DbHelper;
import com.mldz.weatherforecast.db.DbManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Maxim Zubarev on 2019-08-26.
 */
public class ChangeCityModel {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ChangeCityModel(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    private Callable<List<String>> getData() {
        return new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return DbManager.getInstance(context).getCities();
            }
        };
    }

    private <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                        emitter.onNext(func.call());
                    }
                });
    }

    public Observable<List<String>> getCities() {
        return makeObservable(getData());
    }

    public void saveCity(String name) {
        SavePref.getInstance(context).saveCity(name);
    }

    public long addCity(String name) {
        ContentValues cv = new ContentValues();
        cv.put("city", name);
        return db.insert("cities", null, cv);
    }
}
