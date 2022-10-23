package com.fbscodes.myfilemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main_fragmentContainer, new MainFragment());
        fragmentTransaction.commit();

        ImageView addNewMainBtn = (ImageView) findViewById(R.id.add_new_main_btn);
        addNewMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewDialog addNewDialog = new AddNewDialog();
                addNewDialog.show(getSupportFragmentManager(), null);
            }
        });
    }
}