package com.tree.insdownloader.base;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.tree.insdownloader.config.NightModeConfig;
import com.tree.insdownloader.util.LogUtil;

public abstract class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initTheme();
    }

    private void initTheme() {
        //首先判断是否跟随系统模式
        boolean isSystemNode = NightModeConfig.getInstance().getSystemMode(this);
        if (isSystemNode) {
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            boolean isNightMode = NightModeConfig.getInstance().getNightMode(this);
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
}
