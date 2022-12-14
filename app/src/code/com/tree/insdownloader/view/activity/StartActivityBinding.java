package com.tree.insdownloader.view.activity;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;

public class StartActivityBinding extends ViewDataBinding {

    protected StartActivityBinding(DataBindingComponent bindingComponent, View root, int localFieldCount) {
        super(bindingComponent, root, localFieldCount);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object value) {
        return false;
    }

    @Override
    protected void executeBindings() {

    }

    @Override
    public void invalidateAll() {

    }

    @Override
    public boolean hasPendingBindings() {
        return false;
    }
}
