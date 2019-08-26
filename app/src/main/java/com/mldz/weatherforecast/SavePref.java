package com.mldz.weatherforecast;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Maxim Zubarev on 2019-08-26.
 */
public class SavePref {
    private Context context;
    private static SavePref instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SavePref(Context context) {
        this.context = context;
        init();
    }

    public static SavePref getInstance(Context context) {
        if (instance == null) {
            instance = new SavePref(context);
        } else  {
            instance.init();
        }
        return instance;
    }

    private void init() {
        preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    public String getCity() {
        return preferences.getString("city", null);
    }

    public void saveCity(String value) {
        editor = preferences.edit();
        editor.putString("city", value);
        editor.apply();
    }
}
