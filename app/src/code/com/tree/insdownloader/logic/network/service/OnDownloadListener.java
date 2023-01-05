package com.tree.insdownloader.logic.network.service;

import com.tree.insdownloader.logic.model.User;

import okhttp3.Response;

public interface OnDownloadListener {
    void onDownloadSuccess(Response response);

    void onDownloading(int progress);

    void onDownloadFailed(Exception e);

    void onDownloadStart();

}
