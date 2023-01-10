package com.tree.insdownloader.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.util.InsUtil;

public class HomeActivityViewModel extends ViewModel {

    public void jumpIns() {
        InsUtil.jumpIns();
    }
}

