package com.tree.insdownloader.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.util.LogTime;
import com.tree.insdownloader.R;
import com.tree.insdownloader.bean.PhotoMoreBean;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.util.ClipBoardUtil;
import com.tree.insdownloader.util.TypefaceUtil;

import java.util.ArrayList;
import java.util.List;

public class MyDetailView extends LinearLayout implements View.OnClickListener {

    private User user;
    private static final String TAG = "MyDetailView";
    private static final int TAGS = 0;
    private static final int APP_VIEW = 1;
    private static final int CAPTIONS = 2;
    private static final int REPOST = 3;
    private static final int SHARE = 4;
    private static final int DELETE = 5;

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
            for (int index = 0; index < photoMoreBeanList.size(); index++) {
                PhotoMoreBean photoMoreBean = photoMoreBeanList.get(index);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_detail, this, false);
                TextView textName = view.findViewById(R.id.text_name);
                ImageView imagePhoto = view.findViewById(R.id.image_photo);
                textName.setText(photoMoreBean.getName());
                textName.setTypeface(TypefaceUtil.getSemiBoldTypeFace());
                imagePhoto.setImageResource(photoMoreBean.getPhoto());
                view.setTag(index);
                view.setOnClickListener(this);
                addView(view);
            }
        }
    }

    public void setUser(User user) {
        if (user != null) {
            this.user = user;
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (position) {
            case TAGS:
                String describe = user.getDescribe();
                Log.i(TAG,"describe:" + describe);
                break;
            case DELETE:

        }
    }
}


