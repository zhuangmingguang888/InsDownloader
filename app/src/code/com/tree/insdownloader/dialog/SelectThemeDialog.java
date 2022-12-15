package com.tree.insdownloader.dialog;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.SelectThemeDialogAdapter;
import com.tree.insdownloader.base.BaseDialog;
import com.tree.insdownloader.databinding.DialogThemeBinding;
import com.tree.insdownloader.util.DisplayUtil;
import com.tree.insdownloader.viewmodel.SelectDialogViewModel;

public class SelectThemeDialog extends BaseDialog<DialogThemeBinding> {

    private SelectThemeDialogAdapter selectThemeDialogAdapter;

    public SelectThemeDialog(@NonNull Context context) {
        super(context);
        selectThemeDialogAdapter = new SelectThemeDialogAdapter();
    }

    @Override
    protected void initView() {
        Typeface semiBold = Typeface.createFromAsset(mContentView.getContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
        binding.tvTitle.setTypeface(semiBold);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerSelect.setLayoutManager(linearLayoutManager);


    }

    public void setData(String[] strNames) {
        if (strNames!=null && strNames.length == 0) return;
        selectThemeDialogAdapter.setStrNames(strNames);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.dialog_theme;
    }

    @Override
    protected void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = DisplayUtil.dp2px(DisplayUtil.getDisplayWidthInPx(getContext()) * 0.8f);
        layoutParams.height = DisplayUtil.dp2px(DisplayUtil.getDisplayWidthInPx(getContext()) * 0.8f);
        window.setAttributes(layoutParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
