package com.tree.insdownloader.view.fragment;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.graphics.Typeface;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.RecentAdapter;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.base.BaseFragment;
import com.tree.insdownloader.databinding.FragmentHomeBinding;
import com.tree.insdownloader.logic.model.UserInfo;
import com.tree.insdownloader.util.ClipBoardUtil;
import com.tree.insdownloader.view.activity.HomeActivity;
import com.tree.insdownloader.viewmodel.HomeFragmentViewModel;

import java.util.logging.Handler;

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
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.imgEditPaste.setOnClickListener(v -> binding.editUrl.setText(copyClipBoard));
        binding.btnDownload.setOnClickListener(v -> {

        });

        binding.homeWeb.setVm(mViewModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        RecentAdapter adapter = new RecentAdapter(getContext());

        binding.homeRecycleView.setAdapter(adapter);
        binding.homeRecycleView.setLayoutManager(linearLayoutManager);

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
                binding.llFrequently.setVisibility(View.VISIBLE);
                adapter.setUserInfo(userInfo);
            });
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

}
