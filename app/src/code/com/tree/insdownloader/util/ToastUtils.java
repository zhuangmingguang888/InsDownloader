package com.tree.insdownloader.util;

import android.content.Context;
import android.widget.Toast;

import com.tree.insdownloader.app.App;

public class ToastUtils {
    private Toast mToast;


    /*********************** 连续弹出的Toast ************************/
    public static void showToast(int resId) {
        getToast(resId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String text) {
        getToast(text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(int resId) {
        getToast(resId, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(String text) {
        getToast(text, Toast.LENGTH_LONG).show();
    }

    public static Toast getToast(int resId, int duration) { // 连续调用会连续弹出
        return getToast(App.getAppContext().getResources().getText(resId).toString(), duration);
    }

    public static Toast getToast(String text, int duration) {
        return Toast.makeText(App.getAppContext(), text, duration);
    }
}