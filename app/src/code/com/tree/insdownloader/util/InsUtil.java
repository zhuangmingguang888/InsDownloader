package com.tree.insdownloader.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.view.activity.HomeActivity;

import java.net.URL;

public class InsUtil {
    public static final String INS_PACKAGE = "com.instagram.android";

    public static void repostToIns(Context context, Uri uri, String contentType) {
        try {
            repost(context, uri, contentType);
        } catch (Exception e) {
            ToastUtils.showToast("未安装ins");
        }
    }

    public static void shareToIns(Context context, Uri uri, String contentType) {
        try {
            share(context, uri, contentType);
        } catch (Exception e) {
            ToastUtils.showToast("未安装ins");
        }
    }


    private static void repost(Context context, Uri uri, String contentType) throws Exception {
        String type = null;
        if (contentType.contains("JPEG")) {
            type = "image/*";
        } else {
            type = "video/*";
        }
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(type);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setPackage(INS_PACKAGE);
        context.startActivity(shareIntent);
    }

    private static void share(Context context, Uri uri, String contentType) throws Exception {
        String type = null;
        if (contentType.contains("JPEG")) {
            type = "image/*";
        } else {
            type = "video/*";
        }
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(type);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(shareIntent, "share"));
    }

    public static void jumpInsById(Context context, User user) {
        String url = user.getUrl();
        String mediaId = url.split("/")[4];
        Uri uri = Uri.parse("https://instagram.com/p/" + mediaId);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(INS_PACKAGE);
        context.startActivity(intent);
    }

    public static void jumpInsByUserName(String userName) {
        Activity activity = AppManager.getInstance().getActivity(HomeActivity.class);
        Uri uri = Uri.parse("https://instagram.com/" + userName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(INS_PACKAGE);
        activity.startActivity(intent);
    }

}
