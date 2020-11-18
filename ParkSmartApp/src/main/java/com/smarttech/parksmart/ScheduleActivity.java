package com.smarttech.parksmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScheduleActivity extends AppCompatActivity {
    Spinner spinnerDay;
    Spinner spinnerAM;
    Spinner spinnerPM;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        
        //BottomNavigation onSelect function setup
        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


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

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                //Getting reference to "child 1" node
                myRef = myRef.child("Schedule_Start/" + selectDay);
                myRef.setValue(selectStart);
                myRefEnd = myRefEnd.child("Schedule_End/" + selectDay);
                myRefEnd.setValue(selectEnd);

            }
        });
    }
    
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.navigation_availability:
                    Intent intent2 = new Intent(getApplicationContext(), AvailabilityActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.navigation_direction:
                    Intent intent3 = new Intent(getApplicationContext(), DirectionActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.navigation_schedule:
                    Intent intent4 = new Intent(getApplicationContext(), ScheduleActivity.class);
                    startActivity(intent4);
                    break;
                default:
            }
            return false;
        }
    };

}