package com.tree.insdownloader.logic.network.service;

public interface OnDownloadListener {
    void onDownloadSuccess();

    void onDownloading(int progress);

    void onDownloadFailed(Exception e);
}
