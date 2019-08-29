package com.mldz.weatherforecast.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mldz.weatherforecast.utils.model.FullForecast;

import java.util.List;

/**
 * Created by Maxim Zubarev on 2019-08-29.
 */
public class DbManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    private static DbManager instance;

    private DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static DbManager getInstance(Context context) {
        if (instance == null) {
            instance = new DbManager(context);
        }
        return instance;
    }

    public void saveForecast(String city, FullForecast forecast) {
        ContentValues cv = new ContentValues();
        cv.put("city", city);
        cv.put("data", forecast.toJson());

        long u = db.update("weather", cv, "city=?", new String[]{city});
        if (u == 0) {
            u = db.insertWithOnConflict("weather", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public String getForecast(String city) {
        String result = "";
        try (Cursor cursor = db.rawQuery("SELECT data FROM weather WHERE city=?", new String[]{city})) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                result = cursor.getString(cursor.getColumnIndex("data"));
            }
            return result;
        }
    }
}
