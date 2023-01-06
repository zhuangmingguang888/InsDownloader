package com.tree.insdownloader.view.widget;

import static com.tree.insdownloader.config.WebViewConfig.JS_FILE_NAME;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tree.insdownloader.AndroidWebObj;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.config.WebViewConfig;
import com.tree.insdownloader.util.FileUtil;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

import java.util.concurrent.Executors;

public class InsWebView extends WebView {

    private static final String TAG = "InsWebView";
    public static final int PAGE_START = 1;
    public static final int PAGE_FINISH = 2;
    public static final int LOOPER_TIME = 500;

    private int mState = PAGE_START;
    private HomeFragmentViewModel vm;
    private Handler webHandler = new Handler(Looper.getMainLooper());
    private InjectRunnable runnable = new InjectRunnable();

    public void setVm(HomeFragmentViewModel vm) {
        if (vm != null) {
            AndroidWebObj androidWebObj = new AndroidWebObj(vm, listener);
            addJavascriptInterface(androidWebObj, WebViewConfig.JS_OBJ_NAME);
            this.vm = vm;
        }
    }

    public InsWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUserAgentString(WebViewConfig.INS_UA);
        MyWebViewClient webViewClient = new MyWebViewClient();
        setWebViewClient(webViewClient);
    }

    private class MyWebViewClient extends WebViewClient {

        private boolean isFirst;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mState = PAGE_START;
            if (vm != null) {
                vm.setPageState(PAGE_START);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "onPageFinished---" + url);
            if (!isFirst) {
                isFirst = true;
                webHandler.postDelayed(runnable,LOOPER_TIME);
            }
        }
    }

    private DetectJsListener listener = (String userProfile, int collectLength, boolean isStory) -> {
        post(() -> evaluateJavascript("javascript:" + "startCollectData()", null));
    };

    public interface DetectJsListener {
        void onStartReceiveData(String userProfile, int collectLength, boolean isStory);
    }

    private class InjectRunnable implements Runnable {

        @Override
        public void run() {
            webHandler.postDelayed(runnable,LOOPER_TIME);
            String detectJs = FileUtil.readStringFromAssets(App.getAppContext(), JS_FILE_NAME);
            evaluateJavascript("javascript:" + detectJs, null);
            evaluateJavascript("javascript:download()", value -> {
                Log.i(TAG,"value:" + Boolean.parseBoolean(value));
                if (Boolean.parseBoolean(value)) {
                    if (vm != null) {
                        mState = PAGE_FINISH;
                        vm.setPageState(PAGE_FINISH);
                    }
                    clearRunnable();
                }
            });
        }
    }

    private void clearRunnable() {
        webHandler.removeCallbacks(runnable);
    }
}
