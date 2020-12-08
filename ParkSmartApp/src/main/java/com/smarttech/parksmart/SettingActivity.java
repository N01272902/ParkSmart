package com.smarttech.parksmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

public class SettingActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);

        final EditText oldPass = view.findViewById(R.id.oldPassword);
        final EditText newPass = view.findViewById(R.id.newPassword);
        final Button confirmPass = view.findViewById(R.id.confirmPassbtn);
        final Button cancelPass = view.findViewById(R.id.cancelBtn);

        //gets the change pass attribute visibility
        final TextInputLayout inputLayout1 = view.findViewById(R.id.inputlayout1);
        final TextInputLayout inputLayout2 = view.findViewById(R.id.inputlayout2);

        Button chgPass = view.findViewById(R.id.changePasswordbtn);
        chgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputLayout1.setVisibility(View.VISIBLE);
                inputLayout2.setVisibility(View.VISIBLE);
                confirmPass.setVisibility(View.VISIBLE);
                cancelPass.setVisibility(View.VISIBLE);
            }
        });

        cancelPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //refreshes the activity
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingActivity()).commit();
            }
        });

        return view;
    }
}
