package com.tree.insdownloader.util;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.content.Context;
import android.graphics.Typeface;

import com.tree.insdownloader.app.App;

public class TypefaceUtil {

    private static Typeface typeface = Typeface.createFromAsset(App.getAppContext().getAssets(), SEMI_BOLD_ASSETS_PATH);


    public static Typeface getSemiBoldTypeFace() {
        return typeface;
    }

}
