package com.tree.insdownloader.viewmodel;

import android.app.AlertDialog;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tree.insdownloader.logic.dao.UserDao;
import com.tree.insdownloader.logic.dao.UserDatabase;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.util.ClipBoardUtil;
import com.tree.insdownloader.util.FileUtil;

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
        //找出第一个#
        StringBuilder append = new StringBuilder();
        int firstPosition = describe.indexOf("#");
        String firstDescribe = describe.substring(firstPosition + 1);
        String[] tags = firstDescribe.split("#");
        for (int i = 0; i < tags.length; i++) {
            String tag = tags[i];
            append.append("#" + tag.trim());
        }
        int lastPosition = append.lastIndexOf("#");
        String lastString = append.substring(lastPosition);
        Log.i(TAG, "lastString is" + lastString);
        if (lastString.contains(" ")) {
            String[] lastTag = lastString.split(" ");
            append.append(lastTag[0]);
        }
        ClipBoardUtil.clearToClipBoard();
        ClipBoardUtil.copyToClipBoard(append.toString());
        isTagsCopySuccess.setValue(true);
    }

    public void deleteMedia(User user) {
        Executors.newSingleThreadExecutor().execute(() ->
                //删除本地文件

                //删除本地数据库
                userDao.deleteUser(user));
    }


}
