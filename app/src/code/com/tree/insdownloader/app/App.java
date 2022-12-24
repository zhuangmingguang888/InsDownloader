package com.tree.insdownloader.app;

import android.content.Context;

import com.tree.insdownloader.base.BaseApplication;
import com.tree.insdownloader.util.SharedPreferencesUtil;

import xyz.doikki.videoplayer.player.AndroidMediaPlayerFactory;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;

public class App extends BaseApplication {

    private static Context applicationContext = null;

    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();

        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setPlayerFactory(AndroidMediaPlayerFactory.create())
                .build());

    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
