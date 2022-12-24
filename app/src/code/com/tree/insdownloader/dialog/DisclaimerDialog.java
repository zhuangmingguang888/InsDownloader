package com.tree.insdownloader.dialog;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseDialog;
import com.tree.insdownloader.util.DisplayUtil;

public class DisclaimerDialog extends BaseDialog {


    public DisclaimerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        FrameLayout disclaimerGoFl = mContentView.findViewById(R.id.fl_go_it);
        TextView disclaimerText = mContentView.findViewById(R.id.dialog_text_disclaimer);
        TextView disclaimerGo = mContentView.findViewById(R.id.tv_disclaimer_go);
        TextView disclaimerContentText = mContentView.findViewById(R.id.dialog_text_disclaimer_content);
        Typeface semiBold = Typeface.createFromAsset(mContentView.getContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
        disclaimerText.setTypeface(semiBold);
        disclaimerContentText.setTypeface(semiBold);
        disclaimerGo.setTypeface(semiBold);
        disclaimerGoFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });

    }

    @Override
    public int getLayoutID() {
        return R.layout.dialog_disclaimer;
    }

    @Override
    public void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (DisplayUtil.getDisplayWidthInPx(getContext()) * 0.9f);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
