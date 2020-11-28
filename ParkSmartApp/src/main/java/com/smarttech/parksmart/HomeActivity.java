package com.smarttech.parksmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeActivity extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home,container, false);

        //Comment out bcuz it's in bottom nav to prevent app crashing
/*
        ImageButton availabilityButton = view.findViewById(R.id.availability);
        availabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the availability activity
                Intent intent = new Intent(getActivity(), AvailabilityActivity.class);
                startActivity(intent);
            }
        });

 */

        ImageButton lightButton = view.findViewById(R.id.lightcontrol);
        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the availability activity
                Intent intent = new Intent(getActivity(), LightActivity.class);
                startActivity(intent);
            }
        });
/*
        ImageButton scheduleButton= view.findViewById(R.id.schedule);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the schedule View activity
                Intent intent = new Intent(getActivity(), ScheduleViewActivity.class);
                startActivity(intent);
            }
        });
 */

        ImageButton gateButton= view.findViewById(R.id.gatecontrol);
        gateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the gate control activity
                Intent intent = new Intent(getActivity(), GateControlActivity.class);
                startActivity(intent);
            }
        });
/*
        ImageButton directionButton= view.findViewById(R.id.direction);
        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the direction activity
                Intent intent = new Intent(getActivity(), DirectionActivity.class);
                startActivity(intent);
            }
        });
 */

        ImageButton signOutButton = view.findViewById(R.id.signout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SignUpActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}