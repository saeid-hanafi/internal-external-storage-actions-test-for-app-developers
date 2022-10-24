package com.fbscodes.myfilemanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainFragment extends Fragment implements ItemsAdapter.adapterOnClickListener {
    private static final String TAG = "MainFragment";
    private String path;
    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null)
            path = getArguments().getString("path");
        else
            path = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        TextView currentDir = view.findViewById(R.id.tv_current_directory);
        ImageView backIcon = (ImageView) view.findViewById(R.id.iv_main_back_btn);
        recyclerView = view.findViewById(R.id.rv_main_display_items);
        try {
            File file = new File(path);
            File[] files = file.listFiles();
            String lastName = file.getName();
            if (!lastName.equalsIgnoreCase("files"))
                currentDir.setText(lastName);

            assert files != null;
            itemsAdapter = new ItemsAdapter(Arrays.asList(files), this);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(itemsAdapter);
        }catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: Null Path");
        }

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onClickItemListener(File file) {
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        mainActivity.loadFragmentPages(file.getPath());
    }

    @Override
    public void onClickItemDeleteListener(File file) {
        if (file.delete()) {
            itemsAdapter.deleteItem(file);
        }
    }

    @Override
    public void onClickItemCopyListener(File file) {
        try {
            copyItem(file, getCopyDestination(file));
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.loadFragmentPages(getContext().getFilesDir().getPath() + File.separator + "destination");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickItemMoveListener(File file) {
        try {
            copyItem(file, getCopyDestination(file));
            file.delete();
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.loadFragmentPages(getContext().getFilesDir().getPath() + File.separator + "destination");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCopyDestination(File file) {
        return new File(getContext().getFilesDir().getPath() + File.separator + "destination" + File.separator + file.getName());
    }

    public void copyItem(File source, File destination) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(source);
        FileOutputStream fileOutputStream = new FileOutputStream(destination);
        byte[] bytes = new byte[1024];
        int bytesLength;
        while ((bytesLength = fileInputStream.read(bytes)) > 0) {
            fileOutputStream.write(bytes, 0, bytesLength);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }

    public void addNewFolder(String folderName) {
        File newFolder = new File(path+File.separator+folderName);
        if (!newFolder.exists()) {
            if (newFolder.mkdir()) {
                itemsAdapter.addNewItem(newFolder);
                recyclerView.smoothScrollToPosition(0);
            }
        }else{
            Snackbar.make(view, "Folder Name Exists!!", Snackbar.LENGTH_SHORT).show();
        }
    }
}
