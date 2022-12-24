package com.tree.insdownloader.view.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.StartBannerAdapter;
import com.tree.insdownloader.bean.StartBannerBean;
import com.tree.insdownloader.view.activity.HomeActivity;
import com.tree.insdownloader.view.activity.StartActivity;
import com.tree.insdownloader.view.banner.MyBanner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MyGuideView extends FrameLayout {

    private StartBannerAdapter myBannerAdapter;
    private OnGuideItemListener listener;

    public MyGuideView(@NonNull Context context) {
        super(context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.guide_layout, this);
        initGuideData();
        initView(contentView);
    }

    private void initView(View contentView) {
        MyBanner startBanner = contentView.findViewById(R.id.start_banner);
        TextView tvGuideNext = contentView.findViewById(R.id.tv_guide_next);
        FrameLayout flGuideNext = contentView.findViewById(R.id.fl_guide_next);

        startBanner.setAdapter(myBannerAdapter, false);
        startBanner.setUserInputEnabled(false);
        startBanner.init();

        flGuideNext.setOnClickListener(v -> {
            int currentPosition = startBanner.getCurrentItem()+1;
            int count = startBanner.getRealCount() - 1;
            if (currentPosition < count) {
                tvGuideNext.setText(R.string.button_guide_next);
                startBanner.setCurrentItem(currentPosition);
            } else if (currentPosition == count) {
                tvGuideNext.setText(R.string.button_guide_finish);
                startBanner.setCurrentItem(currentPosition);

            } else {
                //通知activity销毁此view
                if (listener != null) {
                    listener.onFinish();
                }
            }
        });
    }

    private void initGuideData() {

        StartBannerBean guideBeanOne = new StartBannerBean(R.mipmap.ic_guide_1, R.string.text_guide_method1, R.string.text_guide_serial_number_one, R.string.text_guide_operator_one, 0);
        StartBannerBean guideBeanTwo = new StartBannerBean(R.mipmap.ic_guide_2, R.string.text_guide_method1, R.string.text_guide_serial_number_two, R.string.text_guide_operator_two, R.string.text_guide_sub_operator_two);
        StartBannerBean guideBeanThree = new StartBannerBean(R.mipmap.ic_guide_3, R.string.text_guide_method2, R.string.text_guide_serial_number_one, R.string.text_guide_operator_three, R.string.text_guide_sub_operator_three);
        StartBannerBean guideBeanFour = new StartBannerBean(R.mipmap.ic_guide_4, R.string.text_guide_method2, R.string.text_guide_serial_number_two, R.string.text_guide_operator_four, R.string.text_guide_sub_operator_four);

        ArrayList<StartBannerBean> bannerBeans = new ArrayList<>();
        bannerBeans.add(guideBeanOne);
        bannerBeans.add(guideBeanTwo);
        bannerBeans.add(guideBeanThree);
        bannerBeans.add(guideBeanFour);

        myBannerAdapter = new StartBannerAdapter(bannerBeans,listener);

    }

    public void setListener(OnGuideItemListener listener) {
        this.listener = listener;
    }

    public interface OnGuideItemListener {
        void onFinish();
        void onClose();

    }

}
