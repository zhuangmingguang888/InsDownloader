package com.tree.insdownloader.view.activity;

import static com.tree.insdownloader.config.JosefinSansFont.ITALIC_ASSETS_PATH;
import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseActivity;
import com.tree.insdownloader.databinding.ActivityHomeBinding;
import com.tree.insdownloader.view.fragment.DownloadFragment;
import com.tree.insdownloader.view.fragment.HomeFragment;
import com.tree.insdownloader.viewmodel.HomeActivityViewModel;


public class HomeActivity extends BaseActivity<HomeActivityViewModel, ActivityHomeBinding> {

    private HomeFragment homeFragment;
    private DownloadFragment downloadFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void processLogic() {
        initBar();
        initHeader();
        initBottom();
        initFragment();

    }

    private void initHeader() {
        Typeface semiBold = Typeface.createFromAsset(getAssets(), SEMI_BOLD_ASSETS_PATH);
        TextView textView = binding.navView.getHeaderView(0).findViewById(R.id.nav_header_text);
        textView.setTypeface(semiBold);
        binding.textHome.setTypeface(semiBold);
        binding.textDownload.setTypeface(semiBold);
        binding.toolbarTitle.setTypeface(semiBold);
        binding.navView.setTopInsetScrimEnabled(false);
    }

    private void initBar() {

        binding.toolbarImgIns.setOnClickListener(v -> {

        });

        binding.toolbarImgHelp.setOnClickListener(v -> {

        });

        binding.toolbarImgMore.setOnClickListener(v -> binding.homeDrawerLayout.openDrawer(GravityCompat.START));


    }

    private void initBottom() {
        binding.textHome.setText(R.string.text_bottom_home);
        binding.textDownload.setText(R.string.text_bottom_download);
        binding.imageHome.setImageResource(R.mipmap.ic_home_select);
        binding.llTabBottom1.setOnClickListener(v -> {
            binding.imageHome.setImageResource(R.mipmap.ic_home_select);
            binding.textHome.setTextColor(getResources().getColor(R.color.text_select, null));

            binding.imageDownload.setImageResource(R.mipmap.ic_downloader_unselect);
            binding.textDownload.setTextColor(getResources().getColor(R.color.text_unselect, null));

        });
        binding.imageDownload.setImageResource(R.mipmap.ic_downloader_unselect);
        binding.llTabBottom2.setOnClickListener(v -> {
            binding.imageDownload.setImageResource(R.mipmap.ic_download_select);
            binding.textDownload.setTextColor(getResources().getColor(R.color.text_select, null));

            binding.imageHome.setImageResource(R.mipmap.ic_home_unselect);
            binding.textHome.setTextColor(getResources().getColor(R.color.text_unselect, null));
        });
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        downloadFragment = new DownloadFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_fragment, homeFragment)
                .add(R.id.fl_fragment, downloadFragment)
                .hide(downloadFragment)
                .show(homeFragment)
                .commitNowAllowingStateLoss();
    }


}