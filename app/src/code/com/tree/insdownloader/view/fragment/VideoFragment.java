package com.tree.insdownloader.view.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;

import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.PhotoAdapter;
import com.tree.insdownloader.base.BaseFragment;
import com.tree.insdownloader.databinding.FragmentPhotoBinding;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.viewmodel.PhotoFragmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends BaseFragment<PhotoFragmentViewModel, FragmentPhotoBinding> {

    private static final String TAG = "VideoFragment";
    private Handler photoHandler;
    private PhotoAdapter adapter;

    public VideoFragment(Context context) {
        initThread();
        adapter = new PhotoAdapter(context);
    }


    @Override
    public void processLogic() {

        mViewModel.getUsersLiveData().observe(this, userList -> {
            if (userList != null && userList.size() > 0) {
                List<User> myUsers = new ArrayList<>();
                for (int i = 0; i < userList.size(); i++) {
                    User user = userList.get(i);
                    String fileName = user.getFileName();
                    if (fileName.contains("mp4")) {
                        Log.d(TAG,"FINE NAME IS" + fileName);
                        myUsers.add(user);
                    }
                }
                adapter.setUserList(myUsers);
            }
        });
        binding.photoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.photoRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        photoHandler.post(() -> mViewModel.getAllUser());
        Log.i(TAG,"onResume");
    }


    private void initThread() {
        HandlerThread handlerThread = new HandlerThread("photoThread");
        handlerThread.start();
        photoHandler = new Handler(handlerThread.getLooper());
    }

    public void setUser(User user) {
        adapter.setUser(user);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo;
    }
}
