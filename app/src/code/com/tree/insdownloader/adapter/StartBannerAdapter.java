package com.tree.insdownloader.adapter;

import static com.tree.insdownloader.config.JosefinSansFont.ITALIC_ASSETS_PATH;
import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tree.insdownloader.R;
import com.tree.insdownloader.bean.StartBannerBean;
import com.tree.insdownloader.databinding.ItemGuideBindingBinding;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class StartBannerAdapter extends BannerAdapter<StartBannerBean, StartBannerAdapter.BannerViewHolder> {

    public StartBannerAdapter(List<StartBannerBean> mDatas) {
        super(mDatas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide_binding,parent,false);
        //DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_guide_binding,parent,false);
        return new BannerViewHolder(itemView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, StartBannerBean data, int position, int size) {
        holder.binding.imgGuide.setImageResource(data.getImageId());
        holder.binding.tvGuideMethod.setText(data.getMethodName());
        holder.binding.tvGuideOperator.setText(data.getOperatorName());
        if (data.getSubOperatorName() == 0) {
            holder.binding.tvGuideSubOperator.setVisibility(View.INVISIBLE);
        } else {
            holder.binding.tvGuideSubOperator.setVisibility(View.VISIBLE);
            holder.binding.tvGuideSubOperator.setText(data.getSubOperatorName());
        }
        holder.binding.tvGuideSerialNumber.setText(data.getSerialNumber());

        holder.binding.imgGuideClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭activity
            }
        });
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {

        private ItemGuideBindingBinding binding;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            Typeface semiBold = Typeface.createFromAsset(itemView.getContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
            Typeface italic = Typeface.createFromAsset(itemView.getContext().getAssets(), ITALIC_ASSETS_PATH);

            binding = ItemGuideBindingBinding.bind(itemView);
            binding.tvGuideMethod.setTypeface(semiBold);
            binding.tvGuideOperator.setTypeface(semiBold);
            binding.tvGuideSerialNumber.setTypeface(semiBold);
            binding.tvGuideSubOperator.setTypeface(italic);

        }
    }

}
