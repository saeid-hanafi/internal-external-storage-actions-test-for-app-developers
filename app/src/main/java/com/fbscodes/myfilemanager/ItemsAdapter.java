package com.fbscodes.myfilemanager;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.itemsViewHolder> {
    private List<File> files;
    private List<File> filteredFiles;
    private adapterOnClickListener eventListener;

    public ItemsAdapter(List<File> files, adapterOnClickListener listener) {
        this.files = new ArrayList<>(files);
        this.filteredFiles = this.files;
        eventListener = (adapterOnClickListener) listener;
    }
    @NonNull
    @Override
    public itemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new itemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull itemsViewHolder holder, int position) {
        holder.bindFiles(filteredFiles.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredFiles.size();
    }

    public void addNewItem(File file) {
        files.add(0, file);
        notifyItemInserted(0);
    }

    public class itemsViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemIcon;
        private TextView fileName;
        private ImageView moreIcon;

        public itemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIcon = itemView.findViewById(R.id.rv_iv_item_icons);
            fileName = itemView.findViewById(R.id.rv_tv_item_text);
            moreIcon = (ImageView) itemView.findViewById(R.id.rv_iv_more_icon);
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

            moreIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.getMenuInflater().inflate(R.menu.more_icon_menu, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete_item:
                                    eventListener.onClickItemDeleteListener(file);
                                    break;
                                case R.id.copy_item:
                                    eventListener.onClickItemCopyListener(file);
                                    break;
                                case R.id.move_item:
                                    eventListener.onClickItemMoveListener(file);
                                    break;
                            }
                            return true;
                        }
                    });
                }
            });
        }
    }

    public void deleteItem(File file) {
        int index = files.indexOf(file);
        if (index > -1) {
            files.remove(index);
            notifyItemRemoved(index);
        }
    }

    public interface adapterOnClickListener {
        void onClickItemListener(File file);
        void onClickItemDeleteListener(File file);
        void onClickItemCopyListener(File file);
        void onClickItemMoveListener(File file);
        void nothingFoundOnSearch();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchItems(String query) {
        if (query.length() > 0) {
            List<File> result = new ArrayList<>();
            for (File file: this.files) {
                if (file.getName().toLowerCase().contains(query.toLowerCase())) {
                    result.add(file);
                }
            }
            if (result.size() > 0) {
                this.filteredFiles = result;
                notifyDataSetChanged();
            }else{
                this.filteredFiles = new ArrayList<>();
                notifyDataSetChanged();
                eventListener.nothingFoundOnSearch();
            }
        }else{
            this.filteredFiles = this.files;
            notifyDataSetChanged();
        }
    }

}
