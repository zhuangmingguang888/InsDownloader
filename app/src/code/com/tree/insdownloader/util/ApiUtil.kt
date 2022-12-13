package com.tree.insdownloader.util

import android.os.Build

object ApiUtil {
    private const val Q = Build.VERSION_CODES.Q
    private const val R = 30
    private const val M = 23


    val isQOrHeight: Boolean
        get() = Build.VERSION.SDK_INT >= Q
    val isROrHeight: Boolean
        get() = Build.VERSION.SDK_INT >= R
    val isMOrHeight: Boolean
        get() = Build.VERSION.SDK_INT >= M

}