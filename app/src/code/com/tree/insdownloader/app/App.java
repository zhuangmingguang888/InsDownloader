package com.tree.insdownloader.app;

import android.content.Context;

import com.tree.insdownloader.base.BaseApplication;

import xyz.doikki.videoplayer.player.AndroidMediaPlayerFactory;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;

public class App extends BaseApplication {

    private static String url;
    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = this;
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setPlayerFactory(AndroidMediaPlayerFactory.create())
                .build());
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        App.url = url;
    }

    public static Context getAppContext() {
        return context;
    }
}
