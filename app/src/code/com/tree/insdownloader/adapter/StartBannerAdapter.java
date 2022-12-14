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
        holder.imageViewGuide.setImageResource(data.getImageId());
        holder.textViewMethod.setText(data.getMethodName());
        holder.textViewOperator.setText(data.getOperatorName());
        if (data.getSubOperatorName() == 0) {
            holder.textViewSubOperator.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewSubOperator.setVisibility(View.VISIBLE);
            holder.textViewSubOperator.setText(data.getSubOperatorName());
        }
        holder.textViewSerialNumber.setText(data.getSerialNumber());

        holder.imageViewGuideClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭activity
            }
        });
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewGuideClose;
        private ImageView imageViewGuide;
        private TextView textViewMethod;
        private TextView textViewSerialNumber;
        private TextView textViewOperator;
        private TextView textViewSubOperator;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            Typeface semiBold = Typeface.createFromAsset(itemView.getContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
            Typeface italic = Typeface.createFromAsset(itemView.getContext().getAssets(), ITALIC_ASSETS_PATH);
            imageViewGuide = itemView.findViewById(R.id.img_guide);
            imageViewGuideClose = itemView.findViewById(R.id.img_guide_close);
            textViewMethod = itemView.findViewById(R.id.tv_guide_method);
            textViewOperator = itemView.findViewById(R.id.tv_guide_operator);
            textViewSerialNumber = itemView.findViewById(R.id.tv_guide_serial_number);
            textViewSubOperator = itemView.findViewById(R.id.tv_guide_sub_operator);

            textViewMethod.setTypeface(semiBold);
            textViewOperator.setTypeface(semiBold);
            textViewSerialNumber.setTypeface(semiBold);
            textViewSubOperator.setTypeface(italic);
        }
    }

}
