package com.tree.insdownloader.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseDialog;
import com.tree.insdownloader.bean.PhotoMoreBean;
import com.tree.insdownloader.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class PhotoMoreDialog extends BaseDialog {

    private LinearLayout llContent;

    public PhotoMoreDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        llContent = mContentView.findViewById(R.id.ll_content);
        initData();

    }

    private void initData() {
        PhotoMoreBean tag = new PhotoMoreBean(R.string.text_photo_tags, R.mipmap.ic_photo_tags);
        PhotoMoreBean app = new PhotoMoreBean(R.string.text_photo_app, R.mipmap.ic_photo_app);
        PhotoMoreBean captions = new PhotoMoreBean(R.string.text_photo_captions, R.mipmap.ic_photo_captions);
        PhotoMoreBean repost = new PhotoMoreBean(R.string.text_photo_repost, R.mipmap.ic_photo_repost);
        PhotoMoreBean share = new PhotoMoreBean(R.string.text_photo_share, R.mipmap.ic_photo_share);
        PhotoMoreBean delete = new PhotoMoreBean(R.string.text_photo_delete, R.mipmap.ic_photo_delete);

        List<PhotoMoreBean> photoMoreBeanList = new ArrayList<>();
        photoMoreBeanList.add(tag);
        photoMoreBeanList.add(app);
        photoMoreBeanList.add(captions);
        photoMoreBeanList.add(repost);
        photoMoreBeanList.add(share);
        photoMoreBeanList.add(delete);

        inflate(photoMoreBeanList);
    }

    public void inflate(List<PhotoMoreBean> photoMoreBeanList) {
        if (photoMoreBeanList != null && photoMoreBeanList.size() > 0) {
            for (PhotoMoreBean photoMoreBean : photoMoreBeanList) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_more, null);
                TextView textName = view.findViewById(R.id.text_name);
                ImageView imagePhoto = view.findViewById(R.id.image_photo);
                textName.setText(photoMoreBean.getName());
                imagePhoto.setImageResource(photoMoreBean.getPhoto());
                llContent.addView(view);
            }
        }
    }


    @Override
    protected int getLayoutID() {
        return R.layout.pop_photo_more;
    }

    @Override
    protected void initWindow() {
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void resetLocation(int x, int y) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = x;
        layoutParams.y = y;
        getWindow().setAttributes(layoutParams);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
    }
}
