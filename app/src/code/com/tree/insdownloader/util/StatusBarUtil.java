package com.tree.insdownloader.util;

import android.content.Context;

import java.lang.reflect.Field;

public class StatusBarUtil {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
