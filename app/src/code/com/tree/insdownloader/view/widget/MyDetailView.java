package com.tree.insdownloader.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tree.insdownloader.R;
import com.tree.insdownloader.bean.PhotoMoreBean;
import com.tree.insdownloader.util.TypefaceUtil;

import java.util.ArrayList;
import java.util.List;

public class MyDetailView extends LinearLayout {

    public MyDetailView(Context context) {
        super(context);
        initData();
    }

    public MyDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        PhotoMoreBean tag = new PhotoMoreBean(R.string.text_photo_tags, R.mipmap.ic_photo_detail_tags);
        PhotoMoreBean app = new PhotoMoreBean(R.string.text_photo_app, R.mipmap.ic_photo_detail_app);
        PhotoMoreBean captions = new PhotoMoreBean(R.string.text_photo_captions, R.mipmap.ic_photo_detail_captions);
        PhotoMoreBean repost = new PhotoMoreBean(R.string.text_photo_repost, R.mipmap.ic_photo_detail_repost);
        PhotoMoreBean share = new PhotoMoreBean(R.string.text_photo_share, R.mipmap.ic_photo_detail_share);
        PhotoMoreBean delete = new PhotoMoreBean(R.string.text_photo_delete, R.mipmap.ic_photo_detail_delete);

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
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_detail, this,false);
                TextView textName = view.findViewById(R.id.text_name);
                ImageView imagePhoto = view.findViewById(R.id.image_photo);
                textName.setText(photoMoreBean.getName());
                textName.setTypeface(TypefaceUtil.getSemiBoldTypeFace());
                imagePhoto.setImageResource(photoMoreBean.getPhoto());
                view.setOnClickListener(v -> {

                });
                addView(view);
            }
        }
    }
}


