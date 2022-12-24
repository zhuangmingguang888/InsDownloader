package com.tree.insdownloader.view.widget;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tree.insdownloader.R;
import com.tree.insdownloader.util.DisplayUtil;

public class MyDrawerLayout extends LinearLayout {

    private Context mContext;

    public MyDrawerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LinearLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.weight = DisplayUtil.getDisplayHeightInPx(context)*0.7f;
        setLayoutParams(layoutParams);
    }

}
