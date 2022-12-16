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

import java.util.List;

public class SelectThemeDialog extends BaseDialog<DialogThemeBinding> {

    private SelectThemeDialogAdapter selectThemeDialogAdapter;
    private List<String> titles;

    public SelectThemeDialog(Context context, List<String> titles) {
        super(context);
        this.selectThemeDialogAdapter = new SelectThemeDialogAdapter(titles);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerSelect.setLayoutManager(linearLayoutManager);
        binding.recyclerSelect.setAdapter(selectThemeDialogAdapter);
    }

    @Override
    protected void initView() {
        Typeface semiBold = Typeface.createFromAsset(getContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
        binding.tvTitle.setTypeface(semiBold);
    }

    public void setData(String strName) {
        if (TextUtils.isEmpty(strName)) return;
        selectThemeDialogAdapter.setTitle(strName);
    }

    public void showContent() {
        if (!isShowing()) {
            show();
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.dialog_theme;
    }

    @Override
    protected void initWindow() {
    /*    Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (DisplayUtil.getDisplayWidthInPx(getContext())*0.8f);
        layoutParams.height = layoutParams.width;
        window.setAttributes(layoutParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
    }

    public void resetWindow(int count) {

        int displayHeight = DisplayUtil.getDisplayHeightInPx(getContext());
        int dp2px = DisplayUtil.dp2px(62);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (DisplayUtil.getDisplayWidthInPx(getContext())*0.8f);
        layoutParams.height = dp2px * (count + 1);
        window.setAttributes(layoutParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
}
