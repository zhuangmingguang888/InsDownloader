package com.tree.insdownloader.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.R;
import com.tree.insdownloader.config.WebViewConfig;
import com.tree.insdownloader.dialog.PhotoMoreDialog;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.util.FileUtil;
import com.tree.insdownloader.util.TypefaceUtil;
import com.tree.insdownloader.view.activity.DetailActivity;
import com.tree.insdownloader.view.widget.MyPopupWindow;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private static final int NONE = -1;
    private static final int TYPE_PHOTO = 1;
    private static final int TYPE_VIDEO = 2;
    private List<User> userList = new ArrayList<>();
    private Context context;
    private PhotoMoreDialog dialog;

    public PhotoAdapter(Context context) {
        this.context = context;
        dialog = new PhotoMoreDialog(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        String name = user.getUserName();
        String fileName = user.getFileName();
        String headFileName = user.getHeadFileName();
        String downPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS + WebViewConfig.DOWNLOAD_INS_ROOT_PATH;
        Uri photoUri = FileUtil.FileGetFromPublic(downPath, fileName);
        Uri headUri = FileUtil.FileGetFromPublic(downPath, headFileName);
        int type = getItemViewType(position);
        if (type == TYPE_PHOTO) {
            holder.imagePlaceHolder.setVisibility(View.GONE);
        } else {
            holder.imagePlaceHolder.setVisibility(View.VISIBLE);
        }
        holder.textName.setText(name);
        holder.textName.setTextColor(context.getColor(R.color.text_photo_name_color));
        holder.textName.setTypeface(TypefaceUtil.getSemiBoldTypeFace());
        holder.imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                holder.llContent.getLocationInWindow(location);
                dialog.resetLocation(location[0] - holder.imageMore.getHeight(), location[1] - holder.imagePhoto.getWidth() / 2);
                dialog.show();
            }
        });
        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uri", photoUri.toString());
                bundle.putParcelable("user",user);
                Intent intent = new Intent(AppManager.getInstance().getTopActivity(), DetailActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("urlBundle",bundle);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(photoUri).into(holder.imagePhoto);
        Glide.with(context).load(headUri).into(holder.imageHeader);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public ImageView imageHeader;
        public ImageView imagePhoto;
        public ImageView imageMore;
        public ImageView imagePlaceHolder;
        public LinearLayout llContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            imageMore = itemView.findViewById(R.id.image_more);
            imagePhoto = itemView.findViewById(R.id.image_photo);
            imageHeader = itemView.findViewById(R.id.image_header);
            llContent = itemView.findViewById(R.id.ll_content);
            imagePlaceHolder = itemView.findViewById(R.id.image_video_place_holder);
        }
    }

    public void setUser(User user) {
        if (user != null) {
            userList.add(user);
            notifyItemInserted(userList.size() - 1);
        }
    }

    public void setUserList(List<User> userList) {
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                setUser(user);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (userList != null && userList.size() > 0) {
            User user = userList.get(position);
            String fileName = user.getFileName();
            if (fileName.contains("jpeg")) {
                return TYPE_PHOTO;
            } else {
                return TYPE_VIDEO;
            }
        }
        return NONE;
    }
}
