package com.tree.insdownloader.viewmodel;


import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tree.insdownloader.app.App;
import com.tree.insdownloader.config.WebViewConfig;
import com.tree.insdownloader.logic.dao.UserDao;
import com.tree.insdownloader.logic.dao.UserDatabase;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.logic.network.service.OkHttpHelper;
import com.tree.insdownloader.logic.network.service.OnDownloadListener;
import com.tree.insdownloader.util.ClipBoardUtil;
import com.tree.insdownloader.util.FileUtil;
import com.tree.insdownloader.util.SharedPreferencesUtil;

import java.io.File;
import java.util.Date;

import retrofit2.Response;

public class HomeFragmentViewModel extends ViewModel {

    private static final String TAG = "HomeFragmentViewModel";
    private static final int DELAY_TIME_1000 = 1000;
    private UserDao userDao = UserDatabase.getInstance().userDao();
    private MutableLiveData<String> clipBoardContent = new MutableLiveData<>();
    private MutableLiveData<UserInfo> userInfoMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> progressMutableLiveData = new MutableLiveData<>();
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

    public void setUser(User user) {
        if (user != null) {
            userMutableLiveData.postValue(user);
        }
    }

    public void setProgress(int progress) {
        progressMutableLiveData.postValue(progress);
    }

    public void downloadByUserinfo(UserInfo userInfo) {
        if (userInfo != null) {
            User user = new User();
            String photoFileName;
            String url;
            String prefix;

            if (!TextUtils.isEmpty(userInfo.getDisplayUrl())) {
                url = userInfo.getDisplayUrl();
                user.setDisplayUrl(url);
                prefix = url.split("_")[1];
                photoFileName = prefix + ".jpeg";
                user.setContentType("image/jpeg");
            } else {
                url = userInfo.getVideoUrl();
                user.setVideoUrl(url);
                int start = url.indexOf("m82/") + 4;
                int end = url.indexOf("_");
                prefix = url.substring(start, end);
                photoFileName = prefix + ".mp4";
                user.setContentType("video/mp4");
            }
            String headFileName = "head" + prefix + ".jpeg";

            user.setHeadUrl(userInfo.getUserProfile().getHeadUrl());
            user.setDescribe(userInfo.getUserProfile().getDescribe());
            user.setUserName(userInfo.getUserProfile().getUserName());
            user.setHeadFileName(headFileName);
            user.setFileName(photoFileName);

            okHttpHelper.download(url, photoFileName, new OnDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    if (user.getContentType().equals("video/mp4")) {
                        Date date = FileUtil.getVideoTime(photoFileName);
                        user.setTime(date.getMinutes() + ":" + date.getSeconds());
                    }
                    double length = FileUtil.getFileOrFilesSize(FileUtil.DOWN_LOAD_PATH + photoFileName, FileUtil.SIZETYPE_MB);
                    user.setContentLength(length + "MB");
                    setUser(user);
                    userDao.insertUser(user);
                    Log.d(TAG, "onDownloadSuccess");
                }

                @Override
                public void onDownloading(int progress) {
                    //3.回调进度
                    setProgress(progress);
                }

                @Override
                public void onDownloadFailed(Exception e) {
                    Log.d(TAG, "onDownloadFailed");

                }

                @Override
                public void onDownloadStart() {
                    setUser(user);
                }
            });
            okHttpHelper.download(userInfo.getUserProfile().getHeadUrl(), headFileName, null);
            setUserInfo(userInfo);
        }
    }

    public MutableLiveData<UserInfo> getUserInfoMutableLiveData() {
        return userInfoMutableLiveData;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Integer> getProgressMutableLiveData() {
        return progressMutableLiveData;
    }

    public void init() {
        HandlerThread vmThread = new HandlerThread("vmThread");
        vmThread.start();
        vmHandler = new Handler(vmThread.getLooper());
    }
}
