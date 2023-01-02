package com.tree.insdownloader.view.fragment;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.FragmentAdapter;
import com.tree.insdownloader.base.BaseFragment;
import com.tree.insdownloader.databinding.FragmentDownloadBinding;
import com.tree.insdownloader.logic.model.User;
import com.tree.insdownloader.util.TypefaceUtil;
import com.tree.insdownloader.viewmodel.DownloadFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class DownloadFragment extends BaseFragment<DownloadFragmentViewModel, FragmentDownloadBinding> {

    private PhotoFragment photoFragment;
    private VideoFragment videoFragment;

    @Override
    public void processLogic() {
        initView();
    }

    private void initView() {
        initTop();
    }

    private void initTop() {
        Typeface semiBoldTypeFace = TypefaceUtil.getSemiBoldTypeFace();
        TextView textPhoto = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
        TextView textVideo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
        textPhoto.setText(R.string.text_download_photo);
        textPhoto.setTypeface(semiBoldTypeFace);
        textPhoto.setTextColor(getContext().getColor(R.color.text_select));

        textVideo.setText(R.string.text_download_video);
        textVideo.setTypeface(semiBoldTypeFace);
        textVideo.setTextColor(getContext().getColor(R.color.text_unselect));


        TabLayout.Tab photoTab = binding.tabLayout.newTab();
        photoTab.setCustomView(textPhoto);
        binding.tabLayout.addTab(photoTab);

        TabLayout.Tab videoTab = binding.tabLayout.newTab();
        videoTab.setCustomView(textVideo);
        binding.tabLayout.addTab(videoTab);

        LinearLayout tabStrip = ((LinearLayout) binding.tabLayout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
            tabStrip.getChildAt(i).setEnabled(false);
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        List<Fragment> fragments = new ArrayList<>();
        photoFragment = new PhotoFragment();
        videoFragment = new VideoFragment();

        fragments.add(photoFragment);
        fragments.add(videoFragment);

        FragmentAdapter fragmentAdapter = new
                FragmentAdapter(fragmentManager, fragments);
        binding.viewPager.setAdapter(fragmentAdapter);
        binding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.tabLayout.selectTab(photoTab);
                    textPhoto.setTextColor(getContext().getColor(R.color.text_select));
                    textVideo.setTextColor(getContext().getColor(R.color.text_unselect));

                } else {
                    binding.tabLayout.selectTab(videoTab);
                    textVideo.setTextColor(getContext().getColor(R.color.text_select));
                    textPhoto.setTextColor(getContext().getColor(R.color.text_unselect));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setUser(User user) {
        if (user.getContentType().contains("jpeg")) {
            photoFragment.setUser(user);
        } else {
            videoFragment.setUser(user);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_download;
    }
}
