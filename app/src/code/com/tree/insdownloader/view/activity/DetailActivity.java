package com.tree.insdownloader.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tree.insdownloader.R;
import com.tree.insdownloader.util.DisplayUtil;

import moe.codeest.enviews.ENPlayView;

public class DetailActivity extends AppCompatActivity {

    private StandardGSYVideoPlayer videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView imageShow = findViewById(R.id.image_show);
        videoPlayer = findViewById(R.id.video_player);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("urlBundle");
            String contentType = bundle.getString("contentType");
            String uri = bundle.getString("uri");
            if (contentType.contains("jpeg")) {
                Glide.with(this).load(uri).into(imageShow);
                imageShow.setVisibility(View.VISIBLE);
                videoPlayer.setVisibility(View.GONE);
            } else {
                imageShow.setVisibility(View.GONE);
                videoPlayer.setVisibility(View.VISIBLE);
                videoPlayer.setUp(uri, false,"");
                videoPlayer.setIsTouchWiget(true);
                videoPlayer.setNeedOrientationUtils(false);
                videoPlayer.setThumbPlay(false);
                videoPlayer.getBackButton().setVisibility(View.GONE);
                videoPlayer.getFullscreenButton().setVisibility(View.GONE);
                videoPlayer.setBottomProgressBarDrawable(null);
                videoPlayer.setDialogProgressColor(getColor(R.color.white),getColor(R.color.white));
                Log.d("DetailActivity -------------------",videoPlayer.getStartButton().getClass() + "");
                videoPlayer.startPlayLogic();

                ENPlayView startButton = (ENPlayView) videoPlayer.getStartButton();
                startButton.setVisibility(View.GONE);

            }
        }

        ImageView imageBack = findViewById(R.id.image_go_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
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
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

}