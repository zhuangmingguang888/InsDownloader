package com.tree.insdownloader.util;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.content.Context;
import android.graphics.Typeface;

public class TypefaceUtil {

    public static Typeface getTypeFace(String typePath, Context context) {
        return Typeface.createFromAsset(context.getAssets(), SEMI_BOLD_ASSETS_PATH);
    }
}
