package com.tree.insdownloader.view.fragment;

import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseFragment;
import com.tree.insdownloader.databinding.FragmentHomeBinding;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

public class HomeFragment extends BaseFragment<HomeFragmentViewModel,FragmentHomeBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


}
