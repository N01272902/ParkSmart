package com.smarttech.parksmart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ScheduleActivity extends AppCompatActivity {
    Spinner spinnerDay;
    Spinner spinnerAM;
    Spinner spinnerPM;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        spinnerDay = (Spinner)findViewById(R.id.Weekday);
        spinnerAM = (Spinner)findViewById(R.id.AM_Time);
        spinnerPM = (Spinner)findViewById(R.id.PM_Time);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDay = spinnerDay.getSelectedItem().toString();
                String selectStart = spinnerAM.getSelectedItem().toString();
                String selectEnd = spinnerPM.getSelectedItem().toString();
                Toast.makeText(ScheduleActivity.this, selectDay + " " + selectStart + " " + selectEnd, Toast.LENGTH_SHORT).show();
            }
        });
    }
}