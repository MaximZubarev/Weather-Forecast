package com.mldz.weatherforecast.mvp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mldz.weatherforecast.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getData() {
        List<String> result = new ArrayList<>();

        Cursor c = db.query("mytable", null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int idCol = c.getColumnIndex("id");
            int cityCol = c.getColumnIndex("city");

            do {
                result.add(c.getString(cityCol));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return result;
    }

    public long addCity(String name) {
        ContentValues cv = new ContentValues();
        cv.put("city", name);
        return db.insert("mytable", null, cv);
    }
}
