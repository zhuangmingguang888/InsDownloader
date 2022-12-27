package com.tree.insdownloader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tree.insdownloader.R;
import com.tree.insdownloader.logic.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    private List<UserInfo> userInfos = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        userInfos.get(position);
    }

    @Override
    public int getItemCount() {
        return userInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            userInfos.add(userInfo);
            notifyItemInserted(userInfos.size() - 1);
        }
    }
}
