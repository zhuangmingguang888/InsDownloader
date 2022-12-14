package com.tree.insdownloader.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends AppCompatActivity {

    protected VM mViewModel;
    protected VDB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        binding = DataBindingUtil.setContentView(this, getContentViewId());
        binding.setLifecycleOwner(this);
        createViewModel();
        processLogic();

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
