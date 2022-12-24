package com.tree.insdownloader.util;

import android.os.Build;

public class ApiUtil {

    public static final int Q = Build.VERSION_CODES.Q;
    public static final int R = Build.VERSION_CODES.R;
    public static final int M = Build.VERSION_CODES.M;
    public static final int N = Build.VERSION_CODES.N;

    public static boolean isMOrHeight() {
        return Build.VERSION.SDK_INT >= M;
    }

    public static boolean isQOrHeight() {
        return Build.VERSION.SDK_INT >= Q;
    }

    public static boolean isROrHeight() {
        return Build.VERSION.SDK_INT >= R;
    }

    public static boolean isNOrHeight() {
        return Build.VERSION.SDK_INT >= N;
    }
}
