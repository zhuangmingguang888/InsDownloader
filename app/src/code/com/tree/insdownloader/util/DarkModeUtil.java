package com.tree.insdownloader.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.tree.insdownloader.ThemeManager;
import com.tree.insdownloader.app.App;

public class DarkModeUtil {

    public static final String KEY_CURRENT_MODEL = "themeType";

    private static int getNightModel() {
        SharedPreferences sp = App.getAppContext().getSharedPreferences(KEY_CURRENT_MODEL, Context.MODE_PRIVATE);
        return sp.getInt(KEY_CURRENT_MODEL, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static void setNightModel(int nightMode) {
        SharedPreferences sp = App.getAppContext().getSharedPreferences(KEY_CURRENT_MODEL, Context.MODE_PRIVATE);
        sp.edit().putInt(KEY_CURRENT_MODEL, nightMode).apply();
    }


    public static void init() {
        int nightMode = getNightModel();
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }


    public static void applyNightMode() {
        setNightModel(AppCompatDelegate.MODE_NIGHT_YES);
    }


    public static void applyDayMode( ) {
        setNightModel(AppCompatDelegate.MODE_NIGHT_NO);
    }


    public static void applySystemMode( ) {
        setNightModel(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }


    public static boolean isDarkMode() {
        int nightMode = getNightModel();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            int applicationUiMode = App.getAppContext().getResources().getConfiguration().uiMode;
            int systemMode = applicationUiMode & Configuration.UI_MODE_NIGHT_MASK;
            return systemMode == Configuration.UI_MODE_NIGHT_YES;
        } else if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            return true;
        } else {
            return false;
        }
    }

}