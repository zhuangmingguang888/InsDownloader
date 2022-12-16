package com.tree.insdownloader.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseDialog;
import com.tree.insdownloader.databinding.DialogAppraiseBinding;
import com.tree.insdownloader.util.DisplayUtil;

public class AppraiseDialog extends BaseDialog<DialogAppraiseBinding> implements CompoundButton.OnCheckedChangeListener {

    public AppraiseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.dialog_appraise;
    }

    @Override
    protected void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (DisplayUtil.getDisplayWidthInPx(getContext()) * 0.8f);
        layoutParams.height =  layoutParams.width;
        window.setAttributes(layoutParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
