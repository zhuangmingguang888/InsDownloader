package com.tree.insdownloader.base;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.config.NightModeConfig;
import com.tree.insdownloader.util.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<VM extends ViewModel, VDB extends ViewDataBinding> extends AppCompatActivity {

    protected VM mViewModel;
    protected VDB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        binding = DataBindingUtil.setContentView(this, getContentViewId());
        binding.setLifecycleOwner(this);
        AppManager.getInstance().addActivity(this);
        createViewModel();
        processLogic();
        initStatusBar();
        LogUtil.v("onCreate clazz:" + this.getClass().getName());
    }

    private void initStatusBar() {
        boolean isNightMode = NightModeConfig.getInstance().getNightMode(getApplicationContext());
        boolean isSystemNode = NightModeConfig.getInstance().getSystemMode(this);
        Window window = getWindow();
        if (isSystemNode) {
            boolean isDark = (getApplicationContext().getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_YES) != 0;
            if (isDark) {
                window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
            } else {
                window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else {
            if (isNightMode) {
                window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
            } else {
                window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void createViewModel() {
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) ViewModelProviders.of(this).get(modelClass);
        }
    }


    protected abstract int getContentViewId();

    //处理逻辑业务
    protected abstract void processLogic();


}
