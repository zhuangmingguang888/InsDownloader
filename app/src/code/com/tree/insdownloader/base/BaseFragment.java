package com.tree.insdownloader.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.tree.insdownloader.R;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<VM extends ViewModel, VDB extends ViewDataBinding> extends Fragment {

    protected VM mViewModel;
    protected VDB binding;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i("fragment","setUserVisibleHint clazz:" + this.getClass().getName());

        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("fragment","onCreate clazz:" + this.getClass().getName());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("fragment","onAttach clazz:" + this.getClass().getName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(),container,false);
        binding.setLifecycleOwner(this);
        Log.i("fragment","onCreateView clazz:" + this.getClass().getName());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i("fragment","onViewCreated clazz:" + this.getClass().getName());
        createViewModel();
        processLogic();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("fragment","onStart clazz:" + this.getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("fragment","onResume clazz:" + this.getClass().getName());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i("fragment","onHiddenChanged clazz:" + this.getClass().getName());
        super.onHiddenChanged(hidden);
    }

    public abstract void processLogic();

    public abstract int getLayoutId();

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

}
