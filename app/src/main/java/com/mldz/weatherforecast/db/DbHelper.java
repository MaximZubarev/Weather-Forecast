package com.mldz.weatherforecast.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

/**
 * Created by Maxim Zubarev on 2019-08-26.
 */
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cities ("
                + "id integer primary key autoincrement,"
                + "city text" + ");");

        db.execSQL("insert into cities (city) values ('London')");
        db.execSQL("insert into cities (city) values ('Moscow')");
        db.execSQL("insert into cities (city) values ('Madrid')");

        db.execSQL("create table weather ("
                + "id integer primary key autoincrement,"
                + "city text,"
                + "data text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
