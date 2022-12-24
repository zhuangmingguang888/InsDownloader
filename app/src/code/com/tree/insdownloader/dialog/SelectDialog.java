package com.tree.insdownloader.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseDialog;
import com.tree.insdownloader.bean.SelectBean;
import com.tree.insdownloader.util.DisplayUtil;
import com.tree.insdownloader.view.widget.MySelectView;

import java.util.ArrayList;
import java.util.List;

public class SelectDialog extends BaseDialog {

    //区分类型
    public static final int THEME_TYPE = 1;
    public static final int LANGUAGE_TYPE = 2;

    //以下是各主题标志
    public static final int SYSTEM_DEFAULT = 1;
    public static final int LIGHT = 2;
    public static final int DARK = 3;

    //以下是各语言标志
    public static final int ENGLISH = 1;
    public static final int AFRIKAANS = 2;
    public static final int ARABIC = 3;
    public static final int CHINESE = 4;
    public static final int CZECH = 5;
    public static final int DANISH = 6;
    public static final int DUTCH = 7;
    public static final int FRENCH = 8;
    private MySelectView selectDialogView;


    public SelectDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        selectDialogView = mContentView.findViewById(R.id.selectDialogView);
        selectDialogView.initHead();
    }

    public void setType(int type) {
        List<SelectBean> selectBeanList = new ArrayList<>();
        if (type == THEME_TYPE) {
            SelectBean selectBean1 = new SelectBean(R.string.dialog_select_theme_default, SYSTEM_DEFAULT, THEME_TYPE);
            SelectBean selectBean2 = new SelectBean(R.string.dialog_select_theme_light, LIGHT, THEME_TYPE);
            SelectBean selectBean3 = new SelectBean(R.string.dialog_select_theme_dark, DARK, THEME_TYPE);
            selectBeanList.add(selectBean1);
            selectBeanList.add(selectBean2);
            selectBeanList.add(selectBean3);
            selectDialogView.setData(selectBeanList);
        } else if (type == LANGUAGE_TYPE) {
            SelectBean selectBean1 = new SelectBean(R.string.dialog_select_language_english, ENGLISH, LANGUAGE_TYPE);
            SelectBean selectBean2 = new SelectBean(R.string.dialog_select_language_afrikaans, AFRIKAANS, LANGUAGE_TYPE);
            SelectBean selectBean3 = new SelectBean(R.string.dialog_select_language_arabic, ARABIC, LANGUAGE_TYPE);
            SelectBean selectBean4 = new SelectBean(R.string.dialog_select_language_chinese, CHINESE, LANGUAGE_TYPE);
            SelectBean selectBean5 = new SelectBean(R.string.dialog_select_language_czech, CZECH, LANGUAGE_TYPE);
            SelectBean selectBean6 = new SelectBean(R.string.dialog_select_language_danish, DANISH, LANGUAGE_TYPE);
            SelectBean selectBean7 = new SelectBean(R.string.dialog_select_language_dutch, DUTCH, LANGUAGE_TYPE);
            SelectBean selectBean8 = new SelectBean(R.string.dialog_select_language_french, FRENCH, LANGUAGE_TYPE);

            selectBeanList.add(selectBean1);
            selectBeanList.add(selectBean2);
            selectBeanList.add(selectBean3);
            selectBeanList.add(selectBean4);
            selectBeanList.add(selectBean5);
            selectBeanList.add(selectBean6);
            selectBeanList.add(selectBean7);
            selectBeanList.add(selectBean8);

            selectDialogView.setData(selectBeanList);
        }
        selectBeanList.clear();
        selectBeanList = null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.dialog_select;
    }

    @Override
    protected void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (DisplayUtil.getDisplayWidthInPx(getContext()) * 0.9f);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
