package com.tree.insdownloader;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.util.LogUtil;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

import org.json.JSONException;

public class AndroidWebObj {

    private static final String TAG = "AndroidWebObj";
    private HomeFragmentViewModel vm;

    public AndroidWebObj(HomeFragmentViewModel vm) {
         this.vm = vm;
    }

    @JavascriptInterface
    public void sendDataJson(String json) throws JSONException {
        LogUtil.e(TAG, "sendDataJson---" + json + Thread.currentThread().getName());
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            UserInfo userInfo = gson.fromJson(json, UserInfo.class);
            vm.downloadByUserinfo(userInfo);
        }
    }

}
