package com.tree.insdownloader.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.tree.insdownloader.R;
import com.tree.insdownloader.ThemeManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseDialog extends Dialog {

    protected View mContentView;

    public BaseDialog(@NonNull Context context) {
        super(context);
        mContentView = LayoutInflater.from(context).inflate(getLayoutID(),null);
        setContentView(mContentView);
        initWindow();
        initView();
    }

    protected abstract void initView();

    protected abstract int getLayoutID();

    protected abstract void initWindow();
}
