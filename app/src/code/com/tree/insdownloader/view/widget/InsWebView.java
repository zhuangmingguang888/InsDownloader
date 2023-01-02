package com.tree.insdownloader.view.widget;

import static com.tree.insdownloader.config.WebViewConfig.INS_VIDEO_URL;
import static com.tree.insdownloader.config.WebViewConfig.JS_FILE_NAME;
import static com.tree.insdownloader.config.WebViewConfig.JS_OBJ_NAME;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tree.insdownloader.AndroidWebObj;
import com.tree.insdownloader.config.WebViewConfig;
import com.tree.insdownloader.util.FileUtil;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

public class InsWebView extends WebView {

    public void setVm(HomeFragmentViewModel vm) {
        if (vm != null) {
            AndroidWebObj androidWebObj = new AndroidWebObj(vm);
            addJavascriptInterface(androidWebObj, WebViewConfig.JS_OBJ_NAME);
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
        public void onPageFinished(WebView view, String url) {
            if (!isFirst) {
                isFirst = true;
                String detectJs = FileUtil.readStringFromAssets(view.getContext(), JS_FILE_NAME);
                evaluateJavascript("javascript:" + detectJs, null);
            }
        }

    }
}
