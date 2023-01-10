package com.tree.insdownloader.view.fragment;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;
import static com.tree.insdownloader.view.activity.HomeActivity.DOWNLOAD_FRAGMENT_TAG;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;

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
import com.tree.insdownloader.util.ToastUtils;
import com.tree.insdownloader.util.TypefaceUtil;
import com.tree.insdownloader.view.widget.InsWebView;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

public class HomeFragment extends BaseFragment<HomeFragmentViewModel, FragmentHomeBinding> {

    private static final String TAG = "HomeFragment";
    private String copyClipBoard;
    private ProgressDialog progressDialog;

    @Override
    public void processLogic() {
        initViewModel();
        initView();
    }

    private void initViewModel() {
        mViewModel.init();
    }

    private void initView() {
        Typeface semiBold = TypefaceUtil.getSemiBoldTypeFace();
        binding.editUrl.setTypeface(semiBold);
        binding.btnDownload.setTypeface(semiBold);
        binding.textFrequently.setTypeface(semiBold);
        binding.textName.setTypeface(semiBold);
        binding.textTime.setTypeface(semiBold);
        binding.textResult.setTypeface(semiBold);
        binding.textSize.setTypeface(semiBold);

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
                if (url.startsWith(WebViewConfig.INS_URL)) {
                    //3.开始加载
                    binding.homeWeb.loadUrl(url);
                    //4.保存url
                    App.setUrl(url);
                    //展示dialog
                    progressDialog = ProgressDialog.show(getContext(), "", getString(R.string.text_loading));
                } else {
                    ToastUtils.showToast("请输入正确的字符串");
                }
            } else {
                ToastUtils.showToast("请输入正确的字符串");
            }
        });

        mViewModel.getUserInfoMutableLiveData().observe(this, userInfo -> {
            //最近的模块
            binding.llFrequently.setVisibility(View.VISIBLE);
            adapter.setUserInfo(userInfo);
        });

        mViewModel.getUserMutableLiveData().observe(this, user -> {
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
                //notifyDownloadFragment(user);
                binding.textSize.setText(user.getContentLength());
            }
        });

        mViewModel.getProgressMutableLiveData().observe(this, progress -> {
            //进度条更新
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.imageResult.setVisibility(View.GONE);
            binding.textResult.setVisibility(View.GONE);
            binding.progressBar.setProgress(progress);
            if (progress == binding.progressBar.getMax()) {
                binding.progressBar.setVisibility(View.GONE);
                binding.imageResult.setVisibility(View.VISIBLE);
                binding.textResult.setVisibility(View.VISIBLE);
            }
        });

        //页面是否加载完成
        mViewModel.getPageStateMutableLiveData().observe(this, state -> {
            if (state == InsWebView.PAGE_START) {
            } else if (state == InsWebView.PAGE_FINISH) {
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
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
        }
    }

    private void notifyDownloadFragment(User user) {
        DownloadFragment downloadFragment = (DownloadFragment) getActivity().getSupportFragmentManager().findFragmentByTag(DOWNLOAD_FRAGMENT_TAG);
        if (downloadFragment != null) {
            downloadFragment.setUser(user);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

}