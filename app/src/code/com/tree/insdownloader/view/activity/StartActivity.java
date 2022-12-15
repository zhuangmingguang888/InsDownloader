package com.tree.insdownloader.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.StartBannerAdapter;
import com.tree.insdownloader.base.BaseActivity;
import com.tree.insdownloader.bean.StartBannerBean;
import com.tree.insdownloader.databinding.ActivityStartBinding;
import com.tree.insdownloader.util.ApiUtil;
import com.tree.insdownloader.util.LogUtil;
import com.tree.insdownloader.util.PermissionUtil;
import com.tree.insdownloader.view.banner.MyBanner;
import com.tree.insdownloader.view.banner.MyBannerConfig;
import com.tree.insdownloader.view.banner.MyCircleIndicator;
import com.tree.insdownloader.viewmodel.StartActivityViewModel;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;


public class StartActivity extends BaseActivity<StartActivityViewModel, ActivityStartBinding> {

    private static final String TAG = "StartActivity";
    private static final int TIME_GO_HOME = 5000;
    private Handler mHandler;
    private GoHomeRunnable goHomeRunnable = new GoHomeRunnable();
    private boolean hasStoragePermission;
    private StartBannerAdapter myBannerAdapter;
    private int nextItem;

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
        initData();
        initUi();
        checkStoragePermission(this);
    }

    private void initData() {
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
    }

    private void startHomeDelay() {
        if (hasStoragePermission) {
            mHandler.post(goHomeRunnable);
        } else {
            //提示无授予完整权限
        }
    }

    private void clearMessage() {
        mHandler.removeCallbacks(goHomeRunnable);
    }

    private void initUi() {
        binding.startBanner.setAdapter(myBannerAdapter, false);
        binding.startBanner.init();
        binding.startBanner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewModel.next.setValue(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.tvGuideNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.next.setValue(++nextItem);
            }
        });

        mViewModel.next.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer position) {
                int realCount = myBannerAdapter.getRealCount() - 1;
                if (position < realCount) {
                    binding.tvGuideNext.setText(R.string.button_guide_next);
                    binding.startBanner.setCurrentItem(position);
                } else if (position == realCount) {
                    binding.tvGuideNext.setText(R.string.button_guide_finish);
                    binding.startBanner.setCurrentItem(realCount);
                } else {
                    startHomeDelay();
                }
            }
        });
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
