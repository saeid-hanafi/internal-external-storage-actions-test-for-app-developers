package com.fbscodes.myfilemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private String path;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = getFilesDir();
        path = file.getPath();
        this.loadFragmentPages(path, false);
        ImageView addNewMainBtn = (ImageView) findViewById(R.id.add_new_main_btn);
        addNewMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewDialog addNewDialog = new AddNewDialog();
                addNewDialog.show(getSupportFragmentManager(), null);
            }
        });
    }

    public void loadFragmentPages(String path, boolean addToBackStack) {
        mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        mainFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main_fragmentContainer, mainFragment);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void loadFragmentPages(String path) {
        this.loadFragmentPages(path, true);
    }
}