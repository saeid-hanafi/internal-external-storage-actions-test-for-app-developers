package com.fbscodes.myfilemanager;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.itemsViewHolder> {

    @NonNull
    @Override
    public itemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull itemsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class itemsViewHolder extends RecyclerView.ViewHolder {

        public itemsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
