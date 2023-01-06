package com.tree.insdownloader;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.util.LogUtil;
import com.tree.insdownloader.view.widget.InsWebView;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AndroidWebObj {

    private static final String TAG = "AndroidWebObj";
    private HomeFragmentViewModel vm;
    private InsWebView.DetectJsListener listener;

    public AndroidWebObj(HomeFragmentViewModel vm) {
        this.vm = vm;
    }

    public AndroidWebObj(HomeFragmentViewModel vm, InsWebView.DetectJsListener listener) {
        this.vm = vm;
        this.listener = listener;
    }

    @JavascriptInterface
    public void sendDataJson(String json,int length) throws JSONException {
        LogUtil.e(TAG, "sendDataJson---" + json + "---length" + length);
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            UserInfo userInfo = gson.fromJson(json, UserInfo.class);
            vm.downloadByUserinfo(userInfo,length);
        }
    }

    @JavascriptInterface
    public void startReceiveData(String userProfile, int collectLength, boolean isStory) {
        if (collectLength > 1) {
            listener.onStartReceiveData(userProfile, collectLength, isStory);
        }
    }




}
