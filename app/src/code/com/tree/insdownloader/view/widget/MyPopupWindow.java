package com.tree.insdownloader.view.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.tree.insdownloader.R;

public class MyPopupWindow {

    private PopupWindow mPopupWindow;
    private Context mContext;

    public MyPopupWindow(Context context) {
        this.mContext = context;
        initWindow();
    }

    private void initWindow() {
        MyPopupWindowView popupView = new MyPopupWindowView(mContext);
        mPopupWindow = new PopupWindow(popupView.getContentView(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
    }

    public void showAsDropDown(View parent, int x, int y) {
        mPopupWindow.showAsDropDown(parent,x,y);
    }
}
