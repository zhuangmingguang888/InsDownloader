package com.tree.insdownloader.view.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.tree.insdownloader.R;
import com.tree.insdownloader.adapter.StartBannerAdapter;
import com.tree.insdownloader.bean.StartBannerBean;
import com.tree.insdownloader.util.DisplayUtil;
import com.youth.banner.Banner;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.transformer.AlphaPageTransformer;
import com.youth.banner.util.BannerUtils;

import java.lang.reflect.Field;

public class MyBanner extends Banner<StartBannerBean, StartBannerAdapter> {

    private MyCircleIndicator indicator;
    private Context context;
    private int indicatorNormalWidth;
    private int indicatorSelectedWidth;
    private int indicatorMarginBottom;


    public MyBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.indicator = new MyCircleIndicator(context);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, com.youth.banner.R.styleable.Banner);
        indicatorNormalWidth = a.getDimensionPixelSize(com.youth.banner.R.styleable.Banner_banner_indicator_normal_width, BannerConfig.INDICATOR_NORMAL_WIDTH);
        indicatorSelectedWidth = a.getDimensionPixelSize(com.youth.banner.R.styleable.Banner_banner_indicator_selected_width, BannerConfig.INDICATOR_SELECTED_WIDTH);
        indicatorMarginBottom = a.getDimensionPixelSize(com.youth.banner.R.styleable.Banner_banner_indicator_marginBottom, BannerConfig.INDICATOR_SELECTED_WIDTH);
    }

    public void init() {
        try {
            setIndicator(indicator, false);
            setIndicatorNormalWidth(indicatorNormalWidth);
            setIndicatorSelectedWidth(indicatorSelectedWidth);

            setIndicatorSelectedColor(context.getColor(R.color.text_guide_method_color));
            setPageTransformer(new AlphaPageTransformer());
            setIndicatorNormalColor(context.getColor(R.color.indicator_guide_normal_color));

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            layoutParams.bottomMargin = indicatorMarginBottom;
            addView(indicator, layoutParams);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
