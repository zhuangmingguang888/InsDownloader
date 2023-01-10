package com.tree.insdownloader.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tree.insdownloader.logic.dao.UserDao;
import com.tree.insdownloader.logic.dao.UserDatabase;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.util.ClipBoardUtil;
import com.tree.insdownloader.util.FileUtil;
import com.tree.insdownloader.util.InsUtil;

import java.util.concurrent.Executors;
import java.util.logging.Handler;


public class DetailActivityViewModel extends ViewModel {
    private static final String TAG = "DetailActivityViewModel";
    private MutableLiveData<Boolean> isTagsCopySuccess = new MutableLiveData<>();
    private UserDao userDao = UserDatabase.getInstance().userDao();

    public MutableLiveData<Boolean> getTagsLiveData() {
        return isTagsCopySuccess;
    }

    public void copyTagsToClipBoard(String describe) {
        StringBuilder append = new StringBuilder();
        int firstPosition = describe.indexOf("#");
        String firstDescribe = describe.substring(firstPosition + 1);
        String[] tags = firstDescribe.split("#");
        for (String tag : tags) {
            append.append("#" + tag.trim());
        }
        int lastPosition = append.lastIndexOf("#");
        String lastString = append.substring(lastPosition);
        if (lastString.contains(" ")) {
            String[] lastTag = lastString.split(" ");
            append.append(lastTag[0]);
        }
        ClipBoardUtil.clearToClipBoard();
        ClipBoardUtil.copyToClipBoard(append.toString());
        isTagsCopySuccess.setValue(true);
    }

    public void deleteMedia(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            FileUtil.deleteInsFile(user.getFileName());
            FileUtil.deleteInsFile(user.getHeadFileName());
            userDao.deleteUser(user);
        });
    }

    public void repostToIns(Context context, User user) {
        Uri uri = FileUtil.FileGetFromPublic(FileUtil.DOWN_LOAD_PATH, user.getFileName());
        if (context != null && uri != null) {
            InsUtil.repostToIns(context, uri, user.getContentType());
        }
    }

    public void shareToIns(Context context, User user) {
        Uri uri = FileUtil.FileGetFromPublic(FileUtil.DOWN_LOAD_PATH, user.getFileName());
        if (context != null && uri != null) {
            InsUtil.shareToIns(context, uri, user.getContentType());
        }
    }

    public void jumpInsById(Context context, User user) {
        if (context != null && user != null) {
            InsUtil.jumpInsById(context, user);
        }
    }


    public void copyDescribeToClipBoard(String describe) {
        ClipBoardUtil.clearToClipBoard();
        ClipBoardUtil.copyToClipBoard(describe);
        isTagsCopySuccess.setValue(true);
    }


}
