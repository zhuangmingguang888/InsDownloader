package com.tree.insdownloader.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tree.insdownloader.R;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.util.InsUtil;
import com.tree.insdownloader.util.TypefaceUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    private List<UserInfo> userInfoList = new ArrayList<>();
    private Set<UserInfo> userInfoSet = new HashSet<>();
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
        UserInfo userInfo = userInfoList.get(position);
        holder.textName.setText(userInfo.getUserProfile().getUserName());
        holder.textName.setTextColor(mContext.getColor(R.color.view_background));
        holder.textName.setTypeface(typeface);
        holder.flContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsUtil.jumpInsByUserName(userInfo.getUserProfile().getUserName());
            }
        });
        Glide.with(mContext).load(userInfo.getUserProfile().getHeadUrl()).into(holder.imageHeader);
    }

    @Override
    public int getItemCount() {
        return userInfoList.size();
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
            if (userInfoSet.contains(userInfo)) return;
            userInfoSet.add(userInfo);
            userInfoList.add(userInfo);
            notifyItemInserted(userInfoList.size() - 1);
        }
    }
}
