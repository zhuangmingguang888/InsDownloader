package com.tree.insdownloader.view.activity;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static com.tree.insdownloader.view.widget.MyDetailView.APP_VIEW;
import static com.tree.insdownloader.view.widget.MyDetailView.CAPTIONS;
import static com.tree.insdownloader.view.widget.MyDetailView.DELETE;
import static com.tree.insdownloader.view.widget.MyDetailView.REPOST;
import static com.tree.insdownloader.view.widget.MyDetailView.SHARE;
import static com.tree.insdownloader.view.widget.MyDetailView.TAGS;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.R;
import com.tree.insdownloader.base.BaseActivity;
import com.tree.insdownloader.databinding.ActivityDetailBinding;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.util.ToastUtils;
import com.tree.insdownloader.viewmodel.DetailActivityViewModel;

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
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
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
                    builder.setMessage(R.string.text_dialog_hint_content);
                    builder.setPositiveButton(R.string.text_dialog_hint_delete, (dialog, which) -> {
                        if (currentUser != null) {
                            mViewModel.deleteMedia(currentUser);
                            AppManager.getInstance().finishActivity(DetailActivity.this);
                        }
                    });

                    builder.setNegativeButton(R.string.text_dialog_hint_cancel, (dialog, which) ->
                            dialog.dismiss());
                    builder.show();
                    break;
                case REPOST:
                    if (currentUser != null) {
                        mViewModel.repostToIns(this, currentUser);
                    }
                    break;
                case SHARE:
                    if (currentUser != null) {
                        mViewModel.shareToIns(this, currentUser);
                    }
                    break;
                case CAPTIONS:
                    if (currentUser != null) {
                        mViewModel.copyDescribeToClipBoard(currentUser.getDescribe());
                    }
                    break;
                case APP_VIEW:
                    if (currentUser != null) {
                        mViewModel.jumpInsById(this, currentUser);
                    }
                    break;
            }
        });

        mViewModel.getTagsLiveData().observe(this, isSuccess -> {
            if (isSuccess) {
                ToastUtils.showToast(R.string.text_copy_success);
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
        binding.videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

}