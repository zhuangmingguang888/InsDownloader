package com.tree.insdownloader.dialog;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseDialog;
import com.tree.insdownloader.databinding.DialogAppraiseBinding;
import com.tree.insdownloader.util.DisplayUtil;

public class AppraiseDialog extends BaseDialog implements CompoundButton.OnCheckedChangeListener {

    public AppraiseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        Typeface semiBold = Typeface.createFromAsset(mContentView.getContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
        TextView textRateTitle = mContentView.findViewById(R.id.text_rate_title);
        TextView textRateSubTitle = mContentView.findViewById(R.id.text_rate_sub_title);
        textRateTitle.setTypeface(semiBold);
        textRateSubTitle.setTypeface(semiBold);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.dialog_appraise;
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
