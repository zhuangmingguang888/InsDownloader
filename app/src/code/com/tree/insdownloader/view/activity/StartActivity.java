package com.tree.insdownloader.view.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tree.insdownloader.R;
import com.tree.insdownloader.util.ApiUtil;
import com.tree.insdownloader.util.PermissionUtil;
import com.tree.insdownloader.util.SharedPreferencesUtil;
import com.tree.insdownloader.view.widget.MyGuideView;
import com.tree.insdownloader.view.widget.MyPrivacyView;


public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";
    private static final int TIME_GO_HOME = 5000;
    private Handler mHandler;
    private GoHomeRunnable goHomeRunnable = new GoHomeRunnable();
    private boolean hasStoragePermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processLogic();
    }

    private void initHandler() {
        mHandler = new Handler(getMainLooper());
    }

    private void checkStoragePermission(Activity activity) {
        PermissionUtil.verifyStoragePermissions(activity);
    }

    public void processLogic() {
        checkStoragePermission(this);
        initHandler();
        initUi();
    }


    private void goHome() {
        mHandler.post(goHomeRunnable);
    }

    private void clearMessage() {
        mHandler.removeCallbacks(goHomeRunnable);
    }

    private void initUi() {
        boolean isFirstIn = SharedPreferencesUtil.getBoolean(this, "isFirstIn", true);
        if (isFirstIn) {
            SharedPreferencesUtil.saveBoolean(this, "isFirstIn", false);
            FrameLayout frameLayout = new FrameLayout(this);
            setContentView(frameLayout);
            ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            frameLayout.setLayoutParams(layoutParams);
            frameLayout.setBackgroundColor(getColor(R.color.windowBackground));
            MyPrivacyView privacyView = new MyPrivacyView(this);
            MyGuideView guideView = new MyGuideView(this);
            frameLayout.addView(privacyView);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            privacyView.setListener(() -> {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
                frameLayout.removeView(privacyView);
                frameLayout.addView(guideView);
            });
            guideView.setListener(new MyGuideView.OnGuideItemListener() {
                @Override
                public void onFinish() {
                    frameLayout.removeAllViews();
                    goHome();
                }

                @Override
                public void onClose() {
                    frameLayout.removeAllViews();
                    goHome();
                }
            });
        } else {
            goHome();
        }
        if (ApiUtil.isMOrHeight()) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionUtil.REQUEST_EXTERNAL_STORAGE) {
            if (ApiUtil.isROrHeight()) {
                if (Environment.isExternalStorageManager()) {
                    hasStoragePermission = true;
                } else {
                    hasStoragePermission = false;
                    clearMessage();
                    finish();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.REQUEST_EXTERNAL_STORAGE) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                hasStoragePermission = true;
            } else {
                hasStoragePermission = false;
                clearMessage();
                finish();
            }
        }
    }

    private class GoHomeRunnable implements Runnable {

        @Override
        public void run() {
            //跳转home
            Intent intent = new Intent(StartActivity.this, HomeActivity.class);
            startActivity(intent);
            clearMessage();
            finish();
        }
    }

}
