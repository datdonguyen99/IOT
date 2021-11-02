package com.example.fragment;

import static com.example.fragment.Constants.HOME_FRAGMENT_INDEX;
import static com.example.fragment.Constants.SETTING_FRAGMENT_INDEX;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    Button btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectFragment(HOME_FRAGMENT_INDEX);

        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                selectFragment(SETTING_FRAGMENT_INDEX);
            }
        });
    }

    public void selectFragment(int pos) {
        Class fragmentClass = null;
        String fragmentTag = "";
        switch (pos) {
            case HOME_FRAGMENT_INDEX:
                fragmentClass = FragmentHome.class;
                fragmentTag = "Home Fragment";
                break;
            case SETTING_FRAGMENT_INDEX:
                fragmentClass = FragmentSetting.class;
                fragmentTag = "Setting Fragment DATA";
                break;
            default:
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();

            Bundle bundle = new Bundle();
            bundle.putString("fragmentTag", fragmentTag);
            bundle.putInt("Fragment number", 10);

            fragment.setArguments(bundle);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_content, fragment).commitAllowingStateLoss();
        } catch (Exception e) {

        }
    }

    public void processDataFragment(int fragment_index, String fragment_data){
        Log.d("Fragment", "data is from: " + fragment_index);
        Log.d("Fragment", "value: " + fragment_data);
    }
}