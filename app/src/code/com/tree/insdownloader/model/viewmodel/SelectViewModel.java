package com.tree.insdownloader.model.viewmodel;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.config.NightModeConfig;
import com.tree.insdownloader.dialog.SelectDialog;
import com.tree.insdownloader.util.LocaleUtil;
import com.tree.insdownloader.util.SharedPreferencesUtil;
import com.tree.insdownloader.view.activity.HomeActivity;

import java.util.Locale;

public class SelectViewModel {

    public void changeTheme(int tag) {
        switch (tag) {
            case SelectDialog.DARK:
                NightModeConfig.getInstance().setNightMode(App.getAppContext(), true);
                NightModeConfig.getInstance().setSystemMode(App.getAppContext(), false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case SelectDialog.LIGHT:
                NightModeConfig.getInstance().setNightMode(App.getAppContext(), false);
                NightModeConfig.getInstance().setSystemMode(App.getAppContext(), false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case SelectDialog.SYSTEM_DEFAULT:
                NightModeConfig.getInstance().setSystemMode(App.getAppContext(), true);
                NightModeConfig.getInstance().setNightMode(App.getAppContext(), false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
        AppManager.getInstance().goHomeActivity(HomeActivity.class);
    }


    public void changeLanguage(int tag) {
        Locale locale = null;
        switch (tag) {
            case SelectDialog.CHINESE:
                locale = Locale.CHINA;
                break;
            case SelectDialog.AFRIKAANS:
                locale = new Locale("af");
                break;
            case SelectDialog.ARABIC:
                locale = new Locale("ar");
                break;
            case SelectDialog.DANISH:
                locale = new Locale("da");
                break;
            case SelectDialog.FRENCH:
                locale = Locale.FRENCH;
                break;
            case SelectDialog.DUTCH:
                locale = new Locale("nl");
                break;
            case SelectDialog.CZECH:
                locale = new Locale("cs");
                break;
            default:
                locale = Locale.ENGLISH;
                break;
        }
        Context context = AppManager.getInstance().getActivity(HomeActivity.class);
        LocaleUtil.changeAllActivityLanguage(locale, context);
        AppManager.getInstance().goHomeActivity(HomeActivity.class);
    }
}
