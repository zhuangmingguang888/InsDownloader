package com.tree.insdownloader.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.view.activity.HomeActivity;

public class JumpInsUtil {

    public static void goInsByUser(String userId){
        Activity activity = AppManager.getInstance().getActivity(HomeActivity.class);
        Uri uri1 = Uri.parse("http://instagram.com/_u/"+userId);
        Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
        intent1.setPackage("com.instagram.android");
        try{
            activity.startActivity(intent1);
        }catch(Exception e){
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/"+userId)));
        }
    }
}
