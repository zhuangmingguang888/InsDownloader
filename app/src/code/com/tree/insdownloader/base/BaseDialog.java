package com.tree.insdownloader.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.tree.insdownloader.R;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseDialog<VDB extends ViewDataBinding> extends Dialog {

    protected VDB binding;
    protected View mContentView;

    public BaseDialog(@NonNull Context context) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutID(), null, false);
        setContentView(binding.getRoot());
        initWindow();
        initView();
    }

    protected abstract void initView();

    protected abstract int getLayoutID();

    protected abstract void initWindow();
}
