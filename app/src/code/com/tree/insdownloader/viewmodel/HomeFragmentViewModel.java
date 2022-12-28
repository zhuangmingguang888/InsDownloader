package com.tree.insdownloader.viewmodel;


import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tree.insdownloader.logic.dao.UserDao;
import com.tree.insdownloader.logic.dao.UserDatabase;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.logic.network.service.OkHttpHelper;
import com.tree.insdownloader.logic.network.service.OnDownloadListener;
import com.tree.insdownloader.util.ClipBoardUtil;
import com.tree.insdownloader.util.SharedPreferencesUtil;

public class HomeFragmentViewModel extends ViewModel {

    private static final String TAG = "HomeFragmentViewModel";
    private static final int DELAY_TIME_1000 = 1000;
    private UserDao userDao = UserDatabase.getInstance().userDao();
    private MutableLiveData<String> clipBoardContent = new MutableLiveData<>();
    private MutableLiveData<UserInfo> userInfoMutableLiveData = new MutableLiveData<>();
    private OkHttpHelper okHttpHelper = new OkHttpHelper();

    private Handler vmHandler;

    public MutableLiveData<String> getClipBoardContent() {
        vmHandler.postDelayed(() -> {
            String strPaste = ClipBoardUtil.paste();
            clipBoardContent.postValue(strPaste);
        }, DELAY_TIME_1000);
        return clipBoardContent;
    }

    public void setUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            userInfoMutableLiveData.postValue(userInfo);
        }
    }

    public void downloadByUserinfo(UserInfo userInfo) {
        if (userInfo !=null) {
            User user = new User();
            String photoFileName;
            String url;

            if (!TextUtils.isEmpty(userInfo.getDisplayUrl())) {
                url = userInfo.getDisplayUrl();
                user.setDisplayUrl(url);
                photoFileName = url.split("_")[1] + ".jpeg";
            } else {
                url = userInfo.getVideoUrl();
                user.setVideoUrl(url);
                photoFileName = url.split("_")[1] + ".mp4";
            }
            String headFileName = "head" + photoFileName;

            user.setHeadUrl(userInfo.getUserProfile().getHeadUrl());
            user.setDescribe(userInfo.getUserProfile().getDescribe());
            user.setUserName(userInfo.getUserProfile().getUserName());
            user.setHeadFileName(headFileName);
            user.setPhotoFileName(photoFileName);
            okHttpHelper.download(url, photoFileName, new OnDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    userDao.insertUser(user);
                }

                @Override
                public void onDownloading(int progress) {

                }

                @Override
                public void onDownloadFailed(Exception e) {
                }
            });
            okHttpHelper.download(userInfo.getUserProfile().getHeadUrl(),headFileName,null);
            setUserInfo(userInfo);
        }
    }

    public MutableLiveData<UserInfo> getUserInfoMutableLiveData() {
        return userInfoMutableLiveData;
    }

    public void init() {
        HandlerThread vmThread = new HandlerThread("vmThread");
        vmThread.start();
        vmHandler = new Handler(vmThread.getLooper());
    }
}
