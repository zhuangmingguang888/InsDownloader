package com.tree.insdownloader.base;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.ThemeManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<VM extends ViewModel, VDB extends ViewDataBinding> extends AppCompatActivity {

    protected VM mViewModel;
    protected VDB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        binding = DataBindingUtil.setContentView(this, getContentViewId());
        binding.setLifecycleOwner(this);
        AppManager.getInstance().addActivity(this);
        createViewModel();
        processLogic();
    }

    private void initTheme() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeManager.getInstance().initTheme();
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
