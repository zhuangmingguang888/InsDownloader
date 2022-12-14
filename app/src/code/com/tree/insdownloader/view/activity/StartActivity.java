package com.tree.insdownloader.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.StartBannerAdapter;
import com.tree.insdownloader.base.BaseActivity;
import com.tree.insdownloader.bean.StartBannerBean;
import com.tree.insdownloader.util.ApiUtil;
import com.tree.insdownloader.util.PermissionUtil;
import com.tree.insdownloader.view.banner.MyBanner;
import com.tree.insdownloader.view.banner.MyBannerConfig;
import com.tree.insdownloader.view.banner.MyCircleIndicator;
import com.tree.insdownloader.viewmodel.StartActivityViewModel;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.util.ArrayList;


public class StartActivity extends BaseActivity<StartActivityViewModel, StartActivityBinding> {

    private static final int TIME_GO_HOME = 5000;
    private Handler mHandler;
    private GoHomeRunnable goHomeRunnable = new GoHomeRunnable();
    private boolean hasStoragePermission;
    private MyBanner myBanner;
    private StartBannerAdapter myBannerAdapter;


    private void initSplashThread() {
        HandlerThread thread = new HandlerThread("timeThread");
        thread.start();
        mHandler = new Handler(thread.getLooper());
    }

    private void checkStoragePermission(Activity activity) {
        PermissionUtil.verifyStoragePermissions(activity);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_start;
    }

    @Override
    public void processLogic() {
        initSplashThread();
        initUi();
        checkStoragePermission(this);
    }

    private void startHomeDelay() {
        if (hasStoragePermission) {

        }
    }

    private void clearMessage() {
        mHandler.removeCallbacks(goHomeRunnable);
    }

    private void initUi() {
        StartBannerBean guideBeanOne = new StartBannerBean(R.mipmap.ic_guide_1, R.string.text_guide_method1, R.string.text_guide_serial_number_one, R.string.text_guide_operator_one, 0);
        StartBannerBean guideBeanTwo = new StartBannerBean(R.mipmap.ic_guide_2, R.string.text_guide_method1, R.string.text_guide_serial_number_two, R.string.text_guide_operator_two, R.string.text_guide_sub_operator_two);
        StartBannerBean guideBeanThree = new StartBannerBean(R.mipmap.ic_guide_3, R.string.text_guide_method2, R.string.text_guide_serial_number_one, R.string.text_guide_operator_three, R.string.text_guide_sub_operator_three);
        StartBannerBean guideBeanFour = new StartBannerBean(R.mipmap.ic_guide_4, R.string.text_guide_method2, R.string.text_guide_serial_number_two, R.string.text_guide_operator_four, R.string.text_guide_sub_operator_four);

        ArrayList<StartBannerBean> bannerBeans = new ArrayList<>();
        bannerBeans.add(guideBeanOne);
        bannerBeans.add(guideBeanTwo);
        bannerBeans.add(guideBeanThree);
        bannerBeans.add(guideBeanFour);

        myBannerAdapter = new StartBannerAdapter(bannerBeans);
        myBanner = findViewById(R.id.start_banner);
        myBanner.setAdapter(myBannerAdapter, false);
        myBanner.init();
        myBanner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == bannerBeans.size()) {
                    //设置button按钮
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (ApiUtil.isMOrHeight()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionUtil.REQUEST_EXTERNAL_STORAGE) {
            if (ApiUtil.isROrHeight()) {
                if (Environment.isExternalStorageManager()) {
                    hasStoragePermission = true;
                    startHomeDelay();
                } else {
                    hasStoragePermission = false;
                    clearMessage();
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
                startHomeDelay();
                hasStoragePermission = true;
            } else {
                hasStoragePermission = false;
                clearMessage();
            }
        }
    }

    private class GoHomeRunnable implements Runnable {

        @Override
        public void run() {

        }
    }

}
