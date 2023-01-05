package com.tree.insdownloader.view.widget;

import static com.tree.insdownloader.config.WebViewConfig.JS_FILE_NAME;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tree.insdownloader.AndroidWebObj;
import com.tree.insdownloader.config.WebViewConfig;
import com.tree.insdownloader.util.FileUtil;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

import java.util.concurrent.Executors;

public class InsWebView extends WebView {

    private HomeFragmentViewModel vm;
    private static final String TAG = "InsWebView";
    public static final int PAGE_START = 1;
    public static final int PAGE_FINISH = 2;

    private int mState = PAGE_START;

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

    public void download() {
        postDelayed(() -> evaluateJavascript("javascript:download()", null), 3000);
    }

    private class MyWebViewClient extends WebViewClient {

        private boolean isFirst;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mState = PAGE_START;
            if (vm != null) {
                vm.setPageState(PAGE_START);
            }
            Log.i(TAG, "onPageStarted---" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "onPageFinished---" + url);
            if (!isFirst) {
                isFirst = true;
                String detectJs = FileUtil.readStringFromAssets(view.getContext(), JS_FILE_NAME);
                evaluateJavascript("javascript:" + detectJs, null);
                mState = PAGE_FINISH;
                if (vm != null) {
                    vm.setPageState(PAGE_FINISH);
                }
            }

        }
    }

    private DetectJsListener listener = (String userProfile, int collectLength, boolean isStory) -> {
        post(() -> evaluateJavascript("javascript:" + "startCollectData()", null));
    };

    public interface DetectJsListener {
        void onStartReceiveData(String userProfile, int collectLength, boolean isStory);
    }
}
