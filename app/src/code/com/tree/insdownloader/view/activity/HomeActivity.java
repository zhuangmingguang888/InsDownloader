package com.tree.insdownloader.view.activity;

import static com.tree.insdownloader.config.JosefinSansFont.ITALIC_ASSETS_PATH;
import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseActivity;
import com.tree.insdownloader.databinding.ActivityHomeBinding;
import com.tree.insdownloader.view.fragment.DownloadFragment;
import com.tree.insdownloader.view.fragment.HomeFragment;
import com.tree.insdownloader.viewmodel.HomeActivityViewModel;

import java.util.Locale;


public class HomeActivity extends BaseActivity<HomeActivityViewModel, ActivityHomeBinding> {

    private static final String TAG = "HomeActivity";
    private HomeFragment homeFragment;
    private DownloadFragment downloadFragment;


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
        textHome.setTypeface(semiBold);
        textDownload.setTypeface(semiBold);
        toolbarTitle.setTypeface(semiBold);

        //顶部点击事件
        toolbarImgIns.setOnClickListener(v -> {

        });

        toolbarImgHelp.setOnClickListener(v -> {

        });


        textHome.setText(R.string.text_bottom_home);
        textDownload.setText(R.string.text_bottom_download);
        imageHome.setImageResource(R.mipmap.ic_home_select);
        llTabBottom1.setOnClickListener(v -> {
            imageHome.setImageResource(R.mipmap.ic_home_select);
            textHome.setTextColor(getResources().getColor(R.color.text_select, null));

            imageDownload.setImageResource(R.mipmap.ic_downloader_unselect);
            textDownload.setTextColor(getResources().getColor(R.color.text_unselect, null));

        });
        imageDownload.setImageResource(R.mipmap.ic_downloader_unselect);
        llTabBottom2.setOnClickListener(v -> {
            imageDownload.setImageResource(R.mipmap.ic_download_select);
            textDownload.setTextColor(getResources().getColor(R.color.text_select, null));

            imageHome.setImageResource(R.mipmap.ic_home_unselect);
            textHome.setTextColor(getResources().getColor(R.color.text_unselect, null));
        });

    }


    private void initFragment() {
        homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_fragment, homeFragment)
                .show(homeFragment)
                .commitNowAllowingStateLoss();
    }


}