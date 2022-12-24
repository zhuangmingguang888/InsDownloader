package com.tree.insdownloader.view.fragment;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.graphics.Typeface;
import android.util.Log;

import com.tree.insdownloader.R;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.base.BaseFragment;
import com.tree.insdownloader.databinding.FragmentHomeBinding;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

public class HomeFragment extends BaseFragment<HomeFragmentViewModel, FragmentHomeBinding> {

    private static final String TAG = "HomeFragment";

    @Override
    public void processLogic() {
        initView();
    }

    private void initView() {
        Typeface semiBold = Typeface.createFromAsset(App.getAppContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
        binding.tvDownload.setTypeface(semiBold);
        binding.tvEditTips.setTypeface(semiBold);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

}
