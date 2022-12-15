package com.tree.insdownloader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tree.insdownloader.R;
import com.tree.insdownloader.databinding.ItemDialogSelectBinding;
import com.tree.insdownloader.viewmodel.SelectDialogViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectThemeDialogAdapter extends RecyclerView.Adapter<SelectThemeDialogAdapter.ViewHolder> {

    private List<String> titles = new ArrayList<>();

    public void setStrName(String title) {
        titles.add(title);
        notifyItemInserted(titles.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_select,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tex
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
