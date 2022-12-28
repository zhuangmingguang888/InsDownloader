package com.tree.insdownloader.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tree.insdownloader.R;
import com.tree.insdownloader.bean.PhotoMoreBean;

import java.util.ArrayList;
import java.util.List;

public class MyPopupWindowView extends FrameLayout {

    private ViewGroup mContentView;

    public MyPopupWindowView(Context context){
        super(context);
        mContentView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pop_photo_more,this,false);
        init();

    }

    private void init() {
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
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_more, this,false);
                TextView textName = view.findViewById(R.id.text_name);
                ImageView imagePhoto = view.findViewById(R.id.image_photo);
                textName.setText(photoMoreBean.getName());
                imagePhoto.setImageResource(photoMoreBean.getPhoto());
                mContentView.addView(view);
            }
        }
    }

    public ViewGroup getContentView() {
        return mContentView;
    }
}
