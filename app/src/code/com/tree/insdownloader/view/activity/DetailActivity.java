package com.tree.insdownloader.view.activity;

import static com.tree.insdownloader.view.widget.MyDetailView.DELETE;
import static com.tree.insdownloader.view.widget.MyDetailView.TAGS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseActivity;
import com.tree.insdownloader.databinding.ActivityDetailBinding;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.util.ToastUtils;
import com.tree.insdownloader.view.widget.MyDetailView;
import com.tree.insdownloader.viewmodel.DetailActivityViewModel;

import retrofit2.http.POST;

public class DetailActivity extends BaseActivity<DetailActivityViewModel, ActivityDetailBinding> {

    private static final String TAG = "DetailActivity";

    @Override
    protected void onPause() {
        super.onPause();
        binding.videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.videoPlayer.onVideoResume();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void processLogic() {
        Intent intent = getIntent();
        User user = null;
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("urlBundle");
            String uri = bundle.getString("uri");
            user = bundle.getParcelable("user");
            binding.detailView.setUser(user);
            if (user.getContentType().contains("jpeg")) {
                Glide.with(this).load(uri).into(binding.imageShow);
                binding.imageShow.setVisibility(View.VISIBLE);
                binding.videoPlayer.setVisibility(View.GONE);
            } else {
                binding.imageShow.setVisibility(View.GONE);
                binding.videoPlayer.setVisibility(View.VISIBLE);
                binding.videoPlayer.setUp(uri, false, "");
                binding.videoPlayer.setIsTouchWiget(true);
                binding.videoPlayer.setNeedOrientationUtils(false);
                binding.videoPlayer.setThumbPlay(false);
                binding.videoPlayer.getBackButton().setVisibility(View.GONE);
                binding.videoPlayer.getFullscreenButton().setVisibility(View.GONE);
                binding.videoPlayer.setBottomProgressBarDrawable(null);
                binding.videoPlayer.setDialogProgressColor(getColor(R.color.white), getColor(R.color.white));
                binding.videoPlayer.startPlayLogic();
            }
        }

        binding.imageGoBack.setOnClickListener(v -> onBackPressed());
        binding.detailView.setListener((position, currentUser) -> {
            switch (position) {
                case TAGS:
                    if (currentUser != null) {
                        mViewModel.copyTagsToClipBoard(currentUser.getDescribe());
                    }
                    break;
                case DELETE:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("媒体文件将被删除，是否继续");
                    builder.setPositiveButton("删除", (dialog, which) -> {
                        mViewModel.deleteMedia(currentUser);
                        AppManager.getInstance().finishActivity(DetailActivity.this);
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
            }
        });

        mViewModel.getTagsLiveData().observe(this, isSuccess -> {
            if (isSuccess) {
                ToastUtils.showToast("文本复制成功");
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
///       不需要回归竖屏
//        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            videoPlayer.getFullscreenButton().performClick();
//            return;
//        }
        //释放所有
        binding.videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

}