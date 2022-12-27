package com.tree.insdownloader.view.widget;

import android.content.Context;

import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.bean.SelectBean;
import com.tree.insdownloader.dialog.SelectDialog;
import com.tree.insdownloader.model.viewmodel.SelectViewModel;
import com.tree.insdownloader.util.LocaleUtil;
import com.tree.insdownloader.util.SharedPreferencesUtil;
import com.tree.insdownloader.view.activity.HomeActivity;

public class SelectViewPresenter {

    private Context mContext;
    private SelectViewModel model;

    public SelectViewPresenter(Context mContext) {
        this.mContext = mContext;
        this.model = new SelectViewModel();
    }

    public void processSelectBean(SelectBean selectBean) {
        int type = selectBean.type;
        int tag = selectBean.tag;
        if (type == SelectDialog.THEME_TYPE) {
            //主题切换
            model.changeTheme(tag);
        } else if (type == SelectDialog.LANGUAGE_TYPE) {
            //语言切换
            model.changeLanguage(tag);
        }
    }
}
