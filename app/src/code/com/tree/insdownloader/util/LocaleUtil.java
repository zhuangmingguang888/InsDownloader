package com.tree.insdownloader.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LocaleUtil {

    public static void changeAllActivityLanguage(Locale locale, Context context) {
        shiftLanguage(locale, context);
    }

    private static void shiftLanguage(Locale locale, Context context) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        if (ApiUtil.isNOrHeight()) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.createConfigurationContext(config);
            Locale.setDefault(locale);
        } else {
            config.locale = locale;
        }
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        resources.updateConfiguration(config, displayMetrics);
    }
}
