package com.tree.insdownloader.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.R;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.util.JumpInsUtil;
import com.tree.insdownloader.util.TypefaceUtil;
import com.tree.insdownloader.view.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    private List<UserInfo> userInfos = new ArrayList<>();
    private Context mContext;

    public RecentAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Typeface typeface = TypefaceUtil.getSemiBoldTypeFace();
        UserInfo userInfo = userInfos.get(position);
        holder.textName.setText(userInfo.getUserProfile().getUserName());
        holder.textName.setTextColor(mContext.getColor(R.color.view_background));
        holder.textName.setTypeface(typeface);
        holder.flContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpInsUtil.goInsByUser(userInfo.getUserProfile().getUserName());
            }
        });
        Glide.with(mContext).load(userInfo.getUserProfile().getHeadUrl()).into(holder.imageHeader);
    }

    @Override
    public int getItemCount() {
        return userInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textName;
        public ImageView imageHeader;
        public FrameLayout flContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            imageHeader = itemView.findViewById(R.id.image_header);
            flContent = itemView.findViewById(R.id.fl_content);
        }
    }

    public void setUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            userInfos.add(userInfo);
            notifyItemInserted(userInfos.size() - 1);
        }
    }
}
