package com.tree.insdownloader.adapter;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tree.insdownloader.R;
import com.tree.insdownloader.bean.StartBannerBean;
import com.tree.insdownloader.databinding.ItemBannerGuideBinding;
import com.tree.insdownloader.view.widget.MyGuideView;
import com.youth.banner.adapter.BannerAdapter;

import java.util.ArrayList;

public class StartBannerAdapter extends BannerAdapter<StartBannerBean, StartBannerAdapter.BannerViewHolder> {

    private MyGuideView.OnGuideItemListener listener;
    public StartBannerAdapter(ArrayList<StartBannerBean> mDatas, MyGuideView.OnGuideItemListener listener) {
        super(mDatas);
        this.listener = listener;
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_guide, parent, false);
        return new BannerViewHolder(itemView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, StartBannerBean data, int position, int size) {
        holder.guideBinding.imgGuide.setImageResource(data.getImageId());
        holder.guideBinding.tvGuideMethod.setText(data.getMethodName());
        holder.guideBinding.tvGuideOperator.setText(data.getOperatorName());
        if (data.getSubOperatorName() == 0) {
            holder.guideBinding.tvGuideSubOperator.setVisibility(View.INVISIBLE);
        } else {
            holder.guideBinding.tvGuideSubOperator.setVisibility(View.VISIBLE);
            holder.guideBinding.tvGuideSubOperator.setText(data.getSubOperatorName());
        }
        holder.guideBinding.tvGuideSerialNumber.setText(data.getSerialNumber());

        holder.guideBinding.imgGuideClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClose();
            }
        });
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {

        private ItemBannerGuideBinding guideBinding;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            Typeface semiBold = Typeface.createFromAsset(itemView.getContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
            guideBinding = ItemBannerGuideBinding.bind(itemView);
            guideBinding.tvGuideMethod.setTypeface(semiBold);
            guideBinding.tvGuideOperator.setTypeface(semiBold);
            guideBinding.tvGuideSerialNumber.setTypeface(semiBold);
            guideBinding.tvGuideSubOperator.setTypeface(semiBold);
            guideBinding.tvGuideTitle.setTypeface(semiBold);

        }
    }
}
