package com.tree.insdownloader.view.activity;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;
import android.graphics.Typeface;
import android.widget.TextView;
import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseActivity;
import com.tree.insdownloader.databinding.ActivityHomeBinding;
import com.tree.insdownloader.viewmodel.HomeActivityViewModel;


public class HomeActivity extends BaseActivity<HomeActivityViewModel, ActivityHomeBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void processLogic() {
        initBar();
        initHeader();
    }

    private void initHeader() {
        binding.navView.setTopInsetScrimEnabled(false);
        Typeface semiBold = Typeface.createFromAsset(getAssets(), SEMI_BOLD_ASSETS_PATH);
        TextView textView = binding.navView.getHeaderView(0).findViewById(R.id.nav_header_text);
        textView.setTypeface(semiBold);
    }

    private void initBar() {
    }


}