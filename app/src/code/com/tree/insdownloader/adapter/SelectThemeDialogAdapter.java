package com.tree.insdownloader.adapter;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tree.insdownloader.R;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.databinding.ItemDialogSelectBinding;
import com.tree.insdownloader.util.LogUtil;
import com.tree.insdownloader.viewmodel.SelectDialogViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectThemeDialogAdapter extends RecyclerView.Adapter<SelectThemeDialogAdapter.ViewHolder> {

    private List<String> titles;
    private List<CheckBox> checkBoxes;

    public SelectThemeDialogAdapter(List<String> titles) {
        this.titles = titles;
        this.checkBoxes = new ArrayList<>();
    }


    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) return;
        titles.add(title);
        notifyItemInserted(titles.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //添加缓存
        checkBoxes.add(holder.binding.cbSelect);
        Typeface semiBold = Typeface.createFromAsset(App.getAppContext().getAssets(), SEMI_BOLD_ASSETS_PATH);
        holder.binding.tvContent.setText(titles.get(position));
        holder.binding.tvContent.setTypeface(semiBold);
        holder.binding.cbSelect.setChecked(position == 0);
        holder.binding.cbSelect.setTag(position);
        holder.binding.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int currentPosition = (int) buttonView.getTag();
                if (isChecked) {
                    for (int i = 0; i < checkBoxes.size(); i++) {
                        CheckBox cbx = checkBoxes.get(i);
                        int cbxPosition = (int) cbx.getTag();
                        if (cbxPosition != currentPosition) {
                            cbx.setChecked(false);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemDialogSelectBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
