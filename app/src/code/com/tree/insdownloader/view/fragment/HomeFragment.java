package com.tree.insdownloader.view.fragment;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;
import static com.tree.insdownloader.view.activity.HomeActivity.DOWNLOAD_FRAGMENT_TAG;

import android.graphics.Typeface;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.RecentAdapter;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.base.BaseFragment;
import com.tree.insdownloader.config.WebViewConfig;
import com.tree.insdownloader.databinding.FragmentHomeBinding;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.util.ClipBoardUtil;
import com.tree.insdownloader.util.ToastUtils;
import com.tree.insdownloader.view.activity.HomeActivity;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

import java.util.Locale;
import java.util.logging.Handler;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class HomeFragment extends BaseFragment<HomeFragmentViewModel, FragmentHomeBinding> {

    private static final String TAG = "HomeFragment";
    private String copyClipBoard;


    @Override
    public void processLogic() {
        initViewModel();
        initView();
    }

    private void initViewModel() {
        mViewModel.init();
    }

    private void initView() {
        Typeface semiBold = Typeface.createFromAsset(App.getAppContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
        binding.editUrl.setTypeface(semiBold);
        binding.btnDownload.setTypeface(semiBold);
        binding.textFrequently.setTypeface(semiBold);
        binding.textName.setTypeface(semiBold);
        binding.textTime.setTypeface(semiBold);
        binding.textResult.setTypeface(semiBold);
        binding.textSize.setTypeface(semiBold);
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.imgEditPaste.setOnClickListener(v -> binding.editUrl.setText(copyClipBoard));

        binding.homeWeb.setVm(mViewModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        RecentAdapter adapter = new RecentAdapter(getContext());

        binding.homeRecycleView.setAdapter(adapter);
        binding.homeRecycleView.setLayoutManager(linearLayoutManager);

        binding.btnDownload.setOnClickListener(v -> {
            //1.判断字符串是否有内容
            String url = binding.editUrl.getText().toString().trim();
            if (!TextUtils.isEmpty(url)) {
                //2.正则匹配字符串是否符合网址形式
                if (copyClipBoard.startsWith(WebViewConfig.INS_URL)) {
                    //3.开始加载
                    binding.homeWeb.loadUrl("https://www.instagram.com/reel/CmivXuyh5L3/?utm_source=ig_web_copy_link");
                } else {
                    ToastUtils.showToast("请输入正确的字符串");
                }
            } else {
                ToastUtils.showToast("请输入正确的字符串");
            }
        });

        if (mViewModel != null) {
            mViewModel.getClipBoardContent().observe(this, clipBoardContent -> {
                if (TextUtils.isEmpty(clipBoardContent)) {
                    binding.imgEditPaste.setImageResource(R.mipmap.ic_edit_unpaste);
                    binding.imgEditPaste.setClickable(false);
                } else {
                    binding.imgEditPaste.setImageResource(R.mipmap.ic_edit_paste);
                    binding.imgEditPaste.setClickable(true);
                }
                copyClipBoard = clipBoardContent;
            });

            mViewModel.getUserInfoMutableLiveData().observe(this, userInfo -> {
                //最近的模块
                binding.llFrequently.setVisibility(View.VISIBLE);
                adapter.setUserInfo(userInfo);
            });

            mViewModel.getUserMutableLiveData().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    binding.clDownload.setVisibility(View.VISIBLE);
                    //还没下载完的情况
                    if (TextUtils.isEmpty(user.getContentLength())) {
                        if (user.getContentType().contains("video/mp4")) {
                            Glide.with(getContext()).load(user.getVideoUrl()).into(binding.imagePhoto);
                        } else {
                            Glide.with(getContext()).load(user.getDisplayUrl()).into(binding.imagePhoto);
                        }
                        Glide.with(getContext()).load(user.getHeadUrl()).into(binding.imageHeader);
                        binding.textName.setText(user.getUserName());
                        binding.imageResult.setVisibility(View.GONE);
                        binding.textResult.setVisibility(View.GONE);
                    } else {
                        binding.textResult.setVisibility(View.VISIBLE);
                        binding.imageResult.setVisibility(View.VISIBLE);
                        binding.textTime.setText(user.getTime());
                        binding.textSize.setText(user.getContentLength());
                        //通知其它fragment作出改变
                        notifyDownloadFragment(user);
                    }
                }

            });

            mViewModel.getProgressMutableLiveData().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer progress) {
                    //进度条更新
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.progressBar.setProgress(progress);
                    if (progress == binding.progressBar.getMax()) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void notifyDownloadFragment(User user) {
        DownloadFragment downloadFragment = (DownloadFragment) getActivity().getSupportFragmentManager().findFragmentByTag(DOWNLOAD_FRAGMENT_TAG);
        downloadFragment.setUser(user);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

}
