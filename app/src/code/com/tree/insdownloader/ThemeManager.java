package com.tree.insdownloader;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.dialog.SelectDialog;
import com.tree.insdownloader.util.SharedPreferencesUtil;
import com.tree.insdownloader.view.activity.HomeActivity;
import com.tree.insdownloader.view.widget.MyNavigationView;

public class ThemeManager {

    private static ThemeManager instance;

    private int navDarkImages[] = {0, R.mipmap.ic_menu_dark_disclaimer, R.mipmap.ic_menu_dark_theme, R.mipmap.ic_menu_dark_share, R.mipmap.ic_menu_dark_rate_us,
            R.mipmap.ic_menu_dark_how_to_work, R.mipmap.ic_menu_dark_about_us, R.mipmap.ic_menu_dark_subscriptions, R.mipmap.ic_menu_dark_privacy_policy, R.mipmap.ic_menu_dark_language_options
    };

    private int navLightImages[] = {0, R.mipmap.ic_header_disclaimer, R.mipmap.ic_header_theme, R.mipmap.ic_header_share, R.mipmap.ic_header_rate_us,
            R.mipmap.ic_header_how_to_work, R.mipmap.ic_header_about_us, R.mipmap.ic_header_manage_subscriptions, R.mipmap.ic_header_privacy_policy, R.mipmap.ic_header_language_options
    };


    private boolean isDarkMode;

    public boolean isDarkMode() {
        return isDarkMode;
    }

    /**
     * 单一实例
     */
    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public void initTheme() {
        Activity activity = AppManager.getInstance().getActivity(HomeActivity.class);
        int themeType = SharedPreferencesUtil.getInt(App.getAppContext(), "themeType", SelectDialog.LIGHT);
        switch (themeType) {
            case SelectDialog.DARK:
                initDarkTheme(activity);
                break;
            case SelectDialog.LIGHT:
                initLightTheme(activity);
                break;
            case SelectDialog.SYSTEM_DEFAULT:
                int flag = App.getAppContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
                if (flag == Configuration.UI_MODE_NIGHT_YES) {
                    initDarkTheme(activity);
                } else {
                    initLightTheme(activity);
                }
                break;
        }
    }

    public void initDarkTheme(Activity activity) {
        //设置左侧菜单暗色主题色
        initDarkNavigationView(activity);
        //设置home暗色主题
        initDarkHome(activity);
        //设置白色字体状态栏
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
        isDarkMode = true;
    }

    public void initLightTheme(Activity activity) {
        //设置左侧菜单亮色主题色
        initLightNavigationView(activity);
        //设置home亮色主题
        initLightHome(activity);
        //设置黑色字体状态栏
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void initLightHome(Activity activity) {
        initLightToolbar(activity);
        initLightContent(activity);
    }

    private void initLightNavigationView(Activity activity) {
        MyNavigationView navigationView = activity.findViewById(R.id.home_navigation_view);
        navigationView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        View head = navigationView.getChildAt(0);
        TextView headTextView = head.findViewById(R.id.nav_header_text);
        headTextView.setTextColor(Color.parseColor("#010101"));
        int childCount = navigationView.getChildCount();
        for (int i = 1; i < childCount; i++) {
            View view = navigationView.getChildAt(i);
            if (view instanceof LinearLayout) {
                TextView textView = view.findViewById(R.id.tv_menu_name);
                ImageView imageView = view.findViewById(R.id.img_menu_picture);
                textView.setTextColor(Color.parseColor("#666666"));
                imageView.setImageResource(navLightImages[i]);
            }
        }

    }


    private void initDarkHome(Activity activity) {
        initDarkToolbar(activity);
        initDarkContent(activity);
    }

    private void initDarkContent(Activity activity) {
        ViewGroup viewGroup = activity.findViewById(R.id.fl_home);
        viewGroup.setBackgroundColor(Color.parseColor("#181818"));
    }

    private void initDarkToolbar(Activity activity) {
        //设置toolbar
        RelativeLayout toolbar = activity.findViewById(R.id.rl_toolbar);
        TextView titleView = toolbar.findViewById(R.id.toolbar_title);
        ImageView moreView = toolbar.findViewById(R.id.toolbar_img_more);
        ImageView helpView = toolbar.findViewById(R.id.toolbar_img_help);
        titleView.setTextColor(Color.parseColor("#FFFFFF"));
        moreView.setImageResource(R.mipmap.ic_menu_dark_more);
        helpView.setImageResource(R.mipmap.ic_menu_dark_help);
    }

    private void initLightContent(Activity activity) {
        ViewGroup viewGroup = activity.findViewById(R.id.fl_home);
        viewGroup.setBackgroundColor(Color.parseColor("#FFFFFF"));

        TextView textHome = activity.findViewById(R.id.text_home);
        TextView textDown = activity.findViewById(R.id.text_download);

    }

    private void initLightToolbar(Activity activity) {
        //设置toolbar
        RelativeLayout toolbar = activity.findViewById(R.id.rl_toolbar);
        TextView titleView = toolbar.findViewById(R.id.toolbar_title);
        ImageView moreView = toolbar.findViewById(R.id.toolbar_img_more);
        ImageView helpView = toolbar.findViewById(R.id.toolbar_img_help);
        titleView.setTextColor(Color.parseColor("#000000"));
        helpView.setImageResource(R.mipmap.ic_menu_help);
        moreView.setImageResource(R.mipmap.ic_menu_more);
    }


    private void initDarkNavigationView(Activity activity) {
        MyNavigationView navigationView = activity.findViewById(R.id.home_navigation_view);
        navigationView.setBackgroundColor(Color.parseColor("#181818"));
        View head = navigationView.getChildAt(0);
        TextView headTextView = head.findViewById(R.id.nav_header_text);
        headTextView.setTextColor(Color.parseColor("#FFFFFF"));
        int childCount = navigationView.getChildCount();
        for (int i = 1; i < childCount; i++) {
            View view = navigationView.getChildAt(i);
            if (view instanceof LinearLayout) {
                TextView textView = view.findViewById(R.id.tv_menu_name);
                ImageView imageView = view.findViewById(R.id.img_menu_picture);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                imageView.setImageResource(navDarkImages[i]);
            }
        }

    }

}
