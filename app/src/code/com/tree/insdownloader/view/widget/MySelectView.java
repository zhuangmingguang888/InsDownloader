package com.tree.insdownloader.view.widget;


import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tree.insdownloader.R;
import com.tree.insdownloader.ThemeManager;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.bean.SelectBean;
import com.tree.insdownloader.dialog.SelectDialog;
import com.tree.insdownloader.util.SharedPreferencesUtil;

import java.util.List;

public class MySelectView extends LinearLayout implements CompoundButton.OnCheckedChangeListener {

    private Typeface semiBold;
    private SelectViewPresenter presenter;
    private TextView textTitle;
    private OnSelectViewListener listener;

    public MySelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        presenter = new SelectViewPresenter(context);
    }

    public void initHead() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_select_head, this, false);
        textTitle = headView.findViewById(R.id.text_select_title);
        semiBold = Typeface.createFromAsset(getContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
        addView(headView);
    }

    public void setData(List<SelectBean> titles) {
        if (titles != null && titles.size() > 0) {
            removeViews(1, getChildCount() - 1);
            inflateInfo(titles);
        }
    }

    public void inflateInfo(List<SelectBean> selectBeanList) {
        for (SelectBean selectBean : selectBeanList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_select, this, false);
            CheckBox checkBox = view.findViewById(R.id.cb_select);
            checkBox.setOnCheckedChangeListener(this);
            checkBox.setTag(selectBean);
            TextView textView = view.findViewById(R.id.tv_content);
            textView.setText(selectBean.title);
            textView.setTypeface(semiBold);
            textTitle.setTypeface(semiBold);
            textTitle.setText(selectBean.type == SelectDialog.LANGUAGE_TYPE ? R.string.menu_language_options : R.string.menu_theme);
            if (ThemeManager.getInstance().isDarkMode()) {
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                textTitle.setTextColor(Color.parseColor("#CFCFCF"));
            } else {
                textView.setTextColor(Color.parseColor("#000000"));
                textTitle.setTextColor(Color.parseColor("#000000"));
            }
            addView(view);
        }
    }

    public void setLastChoose() {
        int tag = SharedPreferencesUtil.getInt(getContext(), "selectTag", 0);
        if (tag != 0) {
            CheckBox cbx = findSelectCheckBox(tag);
            if (cbx != null) {
                cbx.setChecked(true);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CheckBox currentCheckBox = (CheckBox) buttonView;
        if (isChecked) {
            SelectBean bean = (SelectBean) currentCheckBox.getTag();
            CheckBox selectCheckBox = findSelectCheckBox(bean);
            if (selectCheckBox != null) {
                SelectBean selectBean = (SelectBean) selectCheckBox.getTag();
                presenter.processSelectBean(selectBean);
                listener.onSelectItem();
            }
        }
    }

    public CheckBox findSelectCheckBox(SelectBean bean) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 1; i < childCount; i++) {
                View view = getChildAt(i);
                if (view instanceof LinearLayout) {
                    CheckBox checkBox = view.findViewById(R.id.cb_select);
                    SelectBean selectBean = (SelectBean) checkBox.getTag();
                    if (selectBean.tag != bean.tag) {
                        checkBox.setChecked(false);
                    } else {
                        return checkBox;
                    }
                }
            }
        }
        return null;
    }

    public CheckBox findSelectCheckBox(int tag) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 1; i < childCount; i++) {
                View view = getChildAt(i);
                if (view instanceof LinearLayout) {
                    CheckBox checkBox = view.findViewById(R.id.cb_select);
                    SelectBean selectBean = (SelectBean) checkBox.getTag();
                    if (selectBean.tag != tag) {
                        checkBox.setChecked(false);
                    } else {
                        return checkBox;
                    }
                }
            }
        }
        return null;
    }


    public void setListener(OnSelectViewListener listener) {
        this.listener = listener;
    }

    public interface OnSelectViewListener {
        void onSelectItem();
    }
}
