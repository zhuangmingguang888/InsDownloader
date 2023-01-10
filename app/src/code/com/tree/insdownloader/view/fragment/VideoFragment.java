package com.tree.insdownloader.view.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.PhotoAdapter;
import com.tree.insdownloader.base.BaseFragment;
import com.tree.insdownloader.databinding.FragmentPhotoBinding;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.util.LogUtil;
import com.tree.insdownloader.util.TypefaceUtil;
import com.tree.insdownloader.viewmodel.PhotoFragmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends BaseFragment<PhotoFragmentViewModel, FragmentPhotoBinding> {

    private static final String TAG = "VideoFragment";
    private Handler videoHandler;
    private PhotoAdapter adapter;

    public VideoFragment(Context context) {
        initThread();
        adapter = new PhotoAdapter(context);
    }

    @Override
    public void processLogic() {

        binding.textPlaceholder.setTypeface(TypefaceUtil.getSemiBoldTypeFace());
        binding.photoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.photoRecyclerView.setAdapter(adapter);

        mViewModel.getUsersLiveData().observe(this, userList -> {
            int count = 0;
            adapter.removeAllUser();
            if (userList != null && userList.size() > 0) {
                for (int i = 0; i < userList.size(); i++) {
                    User user = userList.get(i);
                    String fileName = user.getFileName();
                    if (fileName.contains("mp4")) {
                        adapter.setUser(user);
                        count++;
                    }
                }
            }
            if (count > 0) {
                binding.rlPlaceholder.setVisibility(View.GONE);
            } else {
                binding.rlPlaceholder.setVisibility(View.VISIBLE);
                binding.textPlaceholder.setText(R.string.text_download_video_placeholder);
                binding.imgPlaceholder.setImageResource(R.mipmap.ic_download_video_placeholder);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        videoHandler.post(() -> mViewModel.getAllUser());
    }

    private void initThread() {
        HandlerThread handlerThread = new HandlerThread("videoThread");
        handlerThread.start();
        videoHandler = new Handler(handlerThread.getLooper());
    }

    public void setUser(User user) {
        adapter.setUser(user);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo;
    }
}
