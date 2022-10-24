package com.fbscodes.myfilemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.itemsViewHolder> {
    private List<File> files;
    private adapterOnClickListener eventListener;

    public ItemsAdapter(List<File> files, adapterOnClickListener listener) {
        this.files = new ArrayList<>(files);
        eventListener = (adapterOnClickListener) listener;
    }
    @NonNull
    @Override
    public itemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new itemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull itemsViewHolder holder, int position) {
        holder.bindFiles(files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void addNewItem(File file) {
        files.add(0, file);
        notifyItemInserted(0);
    }

    public class itemsViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemIcon;
        private TextView fileName;

        public itemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIcon = itemView.findViewById(R.id.rv_iv_item_icons);
            fileName = itemView.findViewById(R.id.rv_tv_item_text);
        }

        public void bindFiles(File file) {
            if (file.isDirectory())
                itemIcon.setImageResource(R.drawable.ic_baseline_folder_open_24);
            else
                itemIcon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
            fileName.setText(file.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.onClickItemListener(file);
                }
            });
        }
    }

    public interface adapterOnClickListener {
        void onClickItemListener(File file);
    }

}
