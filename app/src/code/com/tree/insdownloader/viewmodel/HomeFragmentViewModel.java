package com.tree.insdownloader.viewmodel;


import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tree.insdownloader.util.ClipBoardUtil;

public class HomeFragmentViewModel extends ViewModel {

    private static final String TAG = "HomeFragmentViewModel";
    private static final int DELAY_TIME_1000 = 1000;
    private MutableLiveData<String> clipBoardContent = new MutableLiveData<>();
    private Handler vmHandler;

    public MutableLiveData<String> getClipBoardContent() {
        vmHandler.postDelayed(() -> {
            String strPaste = ClipBoardUtil.paste();
            clipBoardContent.postValue(strPaste);
        }, DELAY_TIME_1000);
        return clipBoardContent;
    }

    public void init() {
        HandlerThread vmThread = new HandlerThread("vmThread");
        vmThread.start();
        vmHandler = new Handler(vmThread.getLooper());
    }
}
