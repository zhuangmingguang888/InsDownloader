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
import com.tree.insdownloader.util.FileUtil;
import com.tree.insdownloader.util.SharedPreferencesUtil;
import com.tree.insdownloader.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import okhttp3.Response;

public class HomeFragmentViewModel extends ViewModel {

    private static final String TAG = "HomeFragmentViewModel";
    private static final int DELAY_TIME_1000 = 1000;
    private static final int THREAD_NUM = 2;

    private UserDao userDao = UserDatabase.getInstance().userDao();
    private MutableLiveData<String> clipBoardContent = new MutableLiveData<>();
    private MutableLiveData<UserInfo> userInfoMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> progressMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> pageStateMutableLiveData = new MutableLiveData<>();

    private OkHttpHelper okHttpHelper = new OkHttpHelper();
    private List<User> userList = new ArrayList<>();
    private Handler vmHandler;

    private final Object MEDIA_LOCK = new Object();
    private final Object HEAD_LOCK = new Object();
    private CountDownLatch mCountDownLatch = new CountDownLatch(THREAD_NUM);
    private int mediaCount;
    private int headCount;
    private int currentCount;

    public MutableLiveData<String> getClipBoardContent() {
        vmHandler.postDelayed(() -> {
            String strPaste = ClipBoardUtil.pastToClipBoard();
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

    public void setPageState(int state) {
        pageStateMutableLiveData.postValue(state);
    }


    public void setProgress(int progress) {
        progressMutableLiveData.postValue(progress);
    }

    public void downloadByUserinfo(UserInfo userInfo, int length) {
        if (userInfo == null) return;

        User user = new User();
        String photoFileName;
        String mediaUrl;
        String prefix;
        String headUrl;

        if (!TextUtils.isEmpty(userInfo.getDisplayUrl())) {
            mediaUrl = userInfo.getDisplayUrl();
            user.setDisplayUrl(mediaUrl);
            prefix = mediaUrl.split("_")[1];
            photoFileName = prefix + ".jpeg";
            user.setContentType("image/jpeg");
        } else {
            mediaUrl = userInfo.getVideoUrl();
            user.setVideoUrl(mediaUrl);
            int start = mediaUrl.indexOf("m82/") + 4;
            int end = mediaUrl.indexOf("_");
            prefix = mediaUrl.substring(start, end);
            photoFileName = prefix + ".mp4";
            user.setContentType("video/mp4");
        }
        String headFileName = "head" + prefix + ".jpeg";
        headUrl = userInfo.getUserProfile().getHeadUrl();
        user.setHeadUrl(headUrl);
        user.setDescribe(userInfo.getUserProfile().getDescribe());
        user.setUserName(userInfo.getUserProfile().getUserName());
        user.setHeadFileName(headFileName);
        user.setFileName(photoFileName);
        setUserInfo(userInfo);
        if (currentCount == length -1) {
            userList.add(user);
        } else {
            userList.add(user);
            currentCount ++;
            return;
        }

/*        User userByFileName = userDao.getUserByFileName(photoFileName);
        if (userByFileName != null){
            ToastUtils.showToast("勿重复下载");
            return;
        }*/

        for (User us : userList) {
            String contentType = us.getContentType();
            if (contentType.contains("image/jpeg")) {
                Log.d(TAG, "us" + us);
                okHttpHelper.download(us.getDisplayUrl(), new OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(Response response) {
                        if (response == null) return;
                        try {
                            synchronized (MEDIA_LOCK) {
                                User user = userList.get(mediaCount);
                                if (user == null) return;
                                FileUtil.contentLength = response.body().contentLength();
                                FileUtil.saveMediaFileToSdcard(user.getFileName(), response.body().byteStream(), this);
                                if (user.getContentType().equals("video/mp4")) {
                                    Date date = FileUtil.getVideoTime(user.getFileName());
                                    user.setTime(date.getMinutes() + ":" + date.getSeconds());
                                }
                                double contentLength = FileUtil.getFileOrFilesSize(FileUtil.DOWN_LOAD_PATH + user.getFileName(), FileUtil.SIZETYPE_MB);
                                user.setContentLength(contentLength + "MB");
                                setUser(user);
                                userDao.insertUser(user);
                                mediaCount++;
                                if (mediaCount == length) {
                                    mCountDownLatch.countDown();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDownloading(int progress) {
                        //3.回调进度
                        setProgress(progress);
                    }

                    @Override
                    public void onDownloadFailed(Exception e) {
                        Log.d(TAG, "onDownloadFailed" + e);

                    }

                    @Override
                    public void onDownloadStart() {
                        setUser(user);
                    }
                });
            } else if (contentType.contains("video/mp4")) {
                okHttpHelper.download(us.getVideoUrl(), new OnDownloadListener() {

                    @Override
                    public void onDownloadSuccess(Response response) {
                        if (response == null) return;
                        try {
                            synchronized (MEDIA_LOCK) {
                                User user = userList.get(mediaCount);
                                if (user == null) return;
                                FileUtil.contentLength = response.body().contentLength();
                                FileUtil.saveMediaFileToSdcard(user.getFileName(), response.body().byteStream(), this);
                                if (user.getContentType().equals("video/mp4")) {
                                    Date date = FileUtil.getVideoTime(user.getFileName());
                                    user.setTime(date.getMinutes() + ":" + date.getSeconds());
                                }
                                double contentLength = FileUtil.getFileOrFilesSize(FileUtil.DOWN_LOAD_PATH + user.getFileName(), FileUtil.SIZETYPE_MB);
                                user.setContentLength(contentLength + "MB");
                                setUser(user);
                                userDao.insertUser(user);
                                mediaCount++;
                                if (mediaCount == length) {
                                    mCountDownLatch.countDown();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onDownloadSuccess");
                    }

                    @Override
                    public void onDownloading(int progress) {
                        //3.回调进度
                        setProgress(progress);
                    }

                    @Override
                    public void onDownloadFailed(Exception e) {
                        Log.d(TAG, "onDownloadFailed" + e);

                    }

                    @Override
                    public void onDownloadStart() {
                        setUser(user);
                    }
                });
            }
            okHttpHelper.download(us.getHeadUrl(), new OnDownloadListener() {
                @Override
                public void onDownloadSuccess(Response response) {
                    if (response == null) return;
                    synchronized (HEAD_LOCK) {
                        try {
                            User user = userList.get(headCount);
                            if (user == null) return;
                            FileUtil.contentLength = response.body().contentLength();
                            FileUtil.saveMediaFileToSdcard(user.getHeadFileName(), response.body().byteStream(), null);
                            headCount++;
                            if (headCount == length) {
                                mCountDownLatch.countDown();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onDownloading(int progress) {

                }

                @Override
                public void onDownloadFailed(Exception e) {

                }

                @Override
                public void onDownloadStart() {

                }
            });
        }
        try {
            //等待所有线程执行完毕
            mCountDownLatch.await();
            clearState();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void clearState() {
        userList.clear();
        currentCount = 0;
        headCount = 0;
        mediaCount = 0;
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

    public MutableLiveData<Integer> getPageStateMutableLiveData() {
        return pageStateMutableLiveData;
    }


    public void init() {
        HandlerThread vmThread = new HandlerThread("vmThread");
        vmThread.start();
        vmHandler = new Handler(vmThread.getLooper());
    }
}
