package com.tree.insdownloader;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.util.LogUtil;
import com.tree.insdownloader.viewmodel.DownloadFragmentViewModel;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

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
        }
    }

}
