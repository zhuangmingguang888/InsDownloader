package com.tree.insdownloader.view.activity;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseActivity;
import com.tree.insdownloader.databinding.ActivityHomeBinding;
import com.tree.insdownloader.view.fragment.DownloadFragment;
import com.tree.insdownloader.view.fragment.HomeFragment;
import com.tree.insdownloader.view.widget.MyNavigationView;
import com.tree.insdownloader.viewmodel.HomeActivityViewModel;


public class HomeActivity extends BaseActivity<HomeActivityViewModel, ActivityHomeBinding> {

    private static final String TAG = "HomeActivity";
    private HomeFragment homeFragment;
    private DownloadFragment downloadFragment;
    public static final String HOME_FRAGMENT_TAG = "home";
    public static final String DOWNLOAD_FRAGMENT_TAG = "download";


    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void processLogic() {
        initView();
        initFragment();
    }

    private void initView() {
        Typeface semiBold = Typeface.createFromAsset(getAssets(), SEMI_BOLD_ASSETS_PATH);

        TextView textHome = findViewById(R.id.text_home);
        TextView textDownload = findViewById(R.id.text_download);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        ImageView imageHome = findViewById(R.id.image_home);
        ImageView imageDownload = findViewById(R.id.image_download);

        ImageView toolbarImgIns = findViewById(R.id.toolbar_img_ins);
        ImageView toolbarImgHelp = findViewById(R.id.toolbar_img_help);
        ImageView toolbarImgMore = findViewById(R.id.toolbar_img_more);
        LinearLayout llTabBottom1 = findViewById(R.id.ll_tab_bottom1);
        LinearLayout llTabBottom2 = findViewById(R.id.ll_tab_bottom2);

        DrawerLayout drawerLayout = findViewById(R.id.home_drawer_layout);
        MyNavigationView navigationView = findViewById(R.id.home_navigation_view);

        textHome.setTypeface(semiBold);
        textDownload.setTypeface(semiBold);
        toolbarTitle.setTypeface(semiBold);

        //顶部点击事件
        toolbarImgIns.setOnClickListener(v -> {

        });

        toolbarImgHelp.setOnClickListener(v -> {

        });

        toolbarImgMore.setOnClickListener(v -> {
            drawerLayout.openDrawer(navigationView);
        });

        toolbarTitle.setText(R.string.toolbar_title);
        textHome.setText(R.string.text_bottom_home);
        textDownload.setText(R.string.text_bottom_download);

        textHome.setTextColor(getColor(R.color.text_select));
        textDownload.setTextColor(getColor(R.color.text_unselect));

        imageHome.setImageResource(R.mipmap.ic_home_select);
        toolbarImgHelp.setImageResource(R.mipmap.ic_menu_help);
        toolbarImgIns.setImageResource(R.mipmap.ic_menu_ins);
        toolbarImgMore.setImageResource(R.mipmap.ic_menu_more);

        llTabBottom1.setOnClickListener(v -> {
            imageHome.setImageResource(R.mipmap.ic_home_select);
            textHome.setTextColor(getResources().getColor(R.color.text_select, null));

            imageDownload.setImageResource(R.mipmap.ic_downloader_unselect);
            textDownload.setTextColor(getResources().getColor(R.color.text_unselect, null));

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.show(homeFragment)
                    .hide(downloadFragment)
                    .commitNowAllowingStateLoss();

        });
        imageDownload.setImageResource(R.mipmap.ic_downloader_unselect);
        llTabBottom2.setOnClickListener(v -> {
            imageDownload.setImageResource(R.mipmap.ic_download_select);
            textDownload.setTextColor(getResources().getColor(R.color.text_select, null));

            imageHome.setImageResource(R.mipmap.ic_home_unselect);
            textHome.setTextColor(getResources().getColor(R.color.text_unselect, null));

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.show(downloadFragment)
                    .hide(homeFragment)
                    .commitNowAllowingStateLoss();

        });

    }


    private void initFragment() {
        homeFragment = new HomeFragment();
        downloadFragment = new DownloadFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_fragment, homeFragment,HOME_FRAGMENT_TAG)
                .add(R.id.fl_fragment,downloadFragment,DOWNLOAD_FRAGMENT_TAG)
                .show(homeFragment)
                .hide(downloadFragment)
                .commitNowAllowingStateLoss();
    }


}