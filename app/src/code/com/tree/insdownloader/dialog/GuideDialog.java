package com.tree.insdownloader.dialog;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseDialog;
import com.tree.insdownloader.view.widget.MyPrivacyView;

public class GuideDialog extends BaseDialog {

    private MyPrivacyView privacyView;


    public GuideDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        privacyView = new MyPrivacyView(getContext());
        privacyView.setListener(() -> {

        });
        setContentView(privacyView);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_start;
    }

    @Override
    protected void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        if (Build.VERSION.SDK_INT >= 28) {
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        window.setAttributes(layoutParams);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int options = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        window.getDecorView().setSystemUiVisibility(options);

    }
}
