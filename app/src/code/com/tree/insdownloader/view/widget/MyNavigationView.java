package com.tree.insdownloader.view.widget;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.tree.insdownloader.R;
import com.tree.insdownloader.bean.MenuBean;
import com.tree.insdownloader.dialog.AppraiseDialog;
import com.tree.insdownloader.dialog.DisclaimerDialog;
import com.tree.insdownloader.dialog.SelectDialog;
import com.tree.insdownloader.util.TypefaceUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.PUT;

public class MyNavigationView extends LinearLayout implements View.OnClickListener {

    public static final int DISCLAIMER = 1;
    public static final int THEME = 2;
    public static final int SHARE = 3;
    public static final int RATE_US = 4;
    public static final int HOW_TO_WORK = 5;
    public static final int ABOUT_US = 6;
    public static final int MANAGER_SUBSCRIPTIONS = 7;
    public static final int PRIVACY_POLICY = 8;
    public static final int LANGUAGE_OPTIONS = 9;

    private Context mContext;
    private List<MenuBean> menuBeanList = new ArrayList<>();
    private DisclaimerDialog disclaimerDialog;
    private SelectDialog selectDialog;
    private AppraiseDialog appraiseDialog;

    public MyNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View header = LayoutInflater.from(context).inflate(R.layout.nav_header_layout, this);
        TextView textHeader = header.findViewById(R.id.nav_header_text);
        textHeader.setTypeface(TypefaceUtil.getSemiBoldTypeFace());
        initInfo();
        initView();
    }

    private void initView() {
        disclaimerDialog = new DisclaimerDialog(mContext);
        selectDialog = new SelectDialog(mContext);
        appraiseDialog = new AppraiseDialog(mContext);
    }

    private void initInfo() {
        MenuBean menuBean1 = new MenuBean(R.string.menu_disclaimer,R.mipmap.ic_header_disclaimer,DISCLAIMER);
        MenuBean menuBean2 = new MenuBean(R.string.menu_theme,R.mipmap.ic_header_theme,THEME);
        MenuBean menuBean3 = new MenuBean(R.string.menu_share,R.mipmap.ic_header_share,SHARE);
        MenuBean menuBean4 = new MenuBean(R.string.menu_rate_us,R.mipmap.ic_header_rate_us,RATE_US);
        MenuBean menuBean5 = new MenuBean(R.string.menu_how_to_work,R.mipmap.ic_header_how_to_work,HOW_TO_WORK);
        MenuBean menuBean6 = new MenuBean(R.string.menu_about_us,R.mipmap.ic_header_about_us,ABOUT_US);
        MenuBean menuBean7 = new MenuBean(R.string.menu_manage_subscriptions,R.mipmap.ic_header_manage_subscriptions,MANAGER_SUBSCRIPTIONS);
        MenuBean menuBean8 = new MenuBean(R.string.menu_privacy_policy,R.mipmap.ic_header_privacy_policy,PRIVACY_POLICY);
        MenuBean menuBean9 = new MenuBean(R.string.menu_language_options,R.mipmap.ic_header_language_options,LANGUAGE_OPTIONS);

        menuBeanList.add(menuBean1);
        menuBeanList.add(menuBean2);
        menuBeanList.add(menuBean3);
        menuBeanList.add(menuBean4);
        menuBeanList.add(menuBean5);
        menuBeanList.add(menuBean6);
        menuBeanList.add(menuBean7);
        menuBeanList.add(menuBean8);
        menuBeanList.add(menuBean9);

        inflateInfo(menuBeanList);
    }

    public void inflateInfo(List<MenuBean> menuBeanList) {
        Typeface semiBold = TypefaceUtil.getSemiBoldTypeFace();
        if (menuBeanList!=null && menuBeanList.size()>0) {
            for (MenuBean menuBean : menuBeanList) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.menu_item,this,false);
                TextView textView = view.findViewById(R.id.tv_menu_name);
                ImageView imageView = view.findViewById(R.id.img_menu_picture);
                LinearLayout linearLayout = view.findViewById(R.id.ll_menu_item);
                textView.setText(menuBean.title);
                textView.setTypeface(semiBold);
                imageView.setImageResource(menuBean.imageId);
                linearLayout.setOnClickListener(this);
                linearLayout.setTag(menuBean.tag);
                addView(view);
            }
        }
    }


    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag();
        switch (tag) {
            case DISCLAIMER:
                disclaimerDialog.show();
                break;
            case THEME:
                selectDialog.setType(SelectDialog.THEME_TYPE);
                selectDialog.show();
                break;
            case RATE_US:
                appraiseDialog.show();
                break;
            case LANGUAGE_OPTIONS:
                selectDialog.setType(SelectDialog.LANGUAGE_TYPE);
                selectDialog.show();
                break;
        }
    }
}
