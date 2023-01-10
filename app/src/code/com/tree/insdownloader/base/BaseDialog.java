package com.tree.insdownloader.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

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
