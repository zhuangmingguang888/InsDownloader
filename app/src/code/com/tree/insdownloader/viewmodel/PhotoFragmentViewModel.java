package com.tree.insdownloader.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tree.insdownloader.logic.dao.UserDao;
import com.tree.insdownloader.logic.dao.UserDatabase;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.logic.model.UserInfo;

import java.util.List;

public class PhotoFragmentViewModel extends ViewModel {

    private static final String TAG = "PhotoFragmentViewModel";
    private UserDao userDao = UserDatabase.getInstance().userDao();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();

    public void getAllUser() {
        List<User> userList = userDao.getAllUser();
        usersLiveData.postValue(userList);
    }

    public void deleteAllUser(List<User> userList) {
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                userDao.deleteUser(user);
            }
        }
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }
}
