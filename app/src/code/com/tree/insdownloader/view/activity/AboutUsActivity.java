package com.tree.insdownloader.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseActivity;
import com.tree.insdownloader.databinding.ActivityAboutUsBinding;
import com.tree.insdownloader.viewmodel.AboutUsActivityViewModel;

public class AboutUsActivity extends BaseActivity<AboutUsActivityViewModel, ActivityAboutUsBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void processLogic() {
        TextView textTitle = findViewById(R.id.toolbar_title);
        ImageView imageMore = findViewById(R.id.toolbar_img_more);
        ImageView imageHelp = findViewById(R.id.toolbar_img_help);
        ImageView imageIns = findViewById(R.id.toolbar_img_ins);
        imageHelp.setVisibility(View.GONE);
        imageIns.setVisibility(View.GONE);
        textTitle.setText(R.string.text_about_us);
        imageMore.setImageResource(R.mipmap.ic_back);
        imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().finishActivity(AboutUsActivity.this);
            }
        });
    }
}