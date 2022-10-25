package com.fbscodes.myfilemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AddNewDialog.addNewDialogListener {
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

        EditText searchInput = findViewById(R.id.main_search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_main_fragmentContainer);
                if (fragment instanceof MainFragment) {
                    ((MainFragment) fragment).searchDocuments(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        MaterialButtonToggleGroup viewBtn = findViewById(R.id.view_sort_toggle_group);
        viewBtn.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_main_fragmentContainer);
                if (fragment instanceof MainFragment) {
                    if (checkedId == R.id.sort_list_view && isChecked) {
                        ((MainFragment) fragment).changeViewType(ViewTypes.ROW);
                    }else if (checkedId == R.id.sort_module_view && isChecked) {
                        ((MainFragment) fragment).changeViewType(ViewTypes.GRID);
                    }
                }
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

    @Override
    public void onCreateNewFolderListener(String folderName) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_main_fragmentContainer);
        if (fragment instanceof MainFragment)
            ((MainFragment) fragment).addNewFolder(folderName);
    }
}