package com.tree.insdownloader.logic.network.service;

import android.content.Context;

import com.tree.insdownloader.app.App;
import com.tree.insdownloader.config.OkHttpConfig;
import com.tree.insdownloader.util.FileUtil;
import com.tree.insdownloader.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHelper {

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(OkHttpConfig.CONNECT_TIME, TimeUnit.SECONDS)
            .readTimeout(OkHttpConfig.READ_TIME, TimeUnit.SECONDS)
            .build();

    public void download(String url, final String destFileName, final OnDownloadListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onDownloadFailed(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.isSuccessful()) {
                        FileUtil.saveMediaFileToSdcard(destFileName, response.body().byteStream(), App.getAppContext());
                        if (listener != null) {
                            listener.onDownloadSuccess();
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onDownloadFailed(e);
                    }
                }
            }
        });
    }


}
