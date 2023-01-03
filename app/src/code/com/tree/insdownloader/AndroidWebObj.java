package com.tree.insdownloader;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.util.LogUtil;
import com.tree.insdownloader.view.widget.InsWebView;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

import org.json.JSONException;

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
    public void sendDataJson(String json) throws JSONException {
        LogUtil.e(TAG, "Json---" + json);
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            UserInfo userInfo = gson.fromJson(json, UserInfo.class);
            vm.downloadByUserinfo(userInfo);
        }
    }

    @JavascriptInterface
    public void startReceiveData(String userProfile, int collectLength, boolean isStory) {
        if (collectLength > 1) {
            listener.onStartReceiveData(userProfile, collectLength, isStory);
        }
    }

}
