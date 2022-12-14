package com.tree.insdownloader.util;

import android.os.Build;

public class ApiUtil {

    public static final int Q = Build.VERSION_CODES.Q;
    public static final int R = 30;
    public static final int M = 23;

    public static boolean isMOrHeight() {
        return Build.VERSION.SDK_INT >= M;
    }

    public static boolean isQOrHeight() {
        return Build.VERSION.SDK_INT >= Q;
    }

    public static boolean isROrHeight() {
        return Build.VERSION.SDK_INT >= R;
    }
}
