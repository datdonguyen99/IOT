package com.example.fragment;

import static com.example.fragment.Constants.HOME_FRAGMENT_INDEX;
import static com.example.fragment.Constants.SETTING_FRAGMENT_INDEX;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSetting extends Fragment {
    Button btnBack;
    TextView txtCaption;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_setting, container, false);

        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity)getActivity()).selectFragment(HOME_FRAGMENT_INDEX);
                ((MainActivity)getActivity()).processDataFragment(SETTING_FRAGMENT_INDEX, "ABC");
            }
        });

        txtCaption = view.findViewById(R.id.txtSettingCaptain);
        String strData = getArguments().getString("fragmentTag");
        Integer intData = getArguments().getInt("fragmentNumber");

        return view;
    }
}
